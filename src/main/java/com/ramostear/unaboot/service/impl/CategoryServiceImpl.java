package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.common.exception.AlreadyExistsException;
import com.ramostear.unaboot.common.exception.NotFoundException;
import com.ramostear.unaboot.common.util.ServiceUtils;
import com.ramostear.unaboot.domain.dto.CategoryDTO;
import com.ramostear.unaboot.domain.entity.Category;
import com.ramostear.unaboot.domain.vo.CategoryVO;
import com.ramostear.unaboot.repository.CategoryRepository;
import com.ramostear.unaboot.service.CategoryService;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ ClassName CategoryServiceImpl
 * @ Description 栏目业务接口实现类
 * @ Author ramostear
 * @ Date 2019/11/13 0013 1:23
 * @ Version 1.0
 **/
@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends UnaService<Category,Integer> implements CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category create(Category category) {
        Assert.notNull(category,"category must not be null.");
        long count = categoryRepository.countByName(category.getName());
        if(count > 0){
            log.warn("category has exist:[{}]",category);
            throw new AlreadyExistsException("category has exist.");
        }
        if(!ServiceUtils.idIsEmpty(category.getParentId())){
            count = categoryRepository.countById(category.getParentId());
            if(count == 0){
                log.warn("parent category with id:[{}] was not found.",category.getParentId());
            }
        }
        return super.create(category);
    }

    @Override
    public List<CategoryVO> tree(Sort sort) {
        Assert.notNull(sort,"sort must not be null");
        List<Category> categories = listAll(sort);
        if(CollectionUtils.isEmpty(categories)){
            return Collections.emptyList();
        }
        CategoryVO root = getRoot();
        initNodes(root,categories);
        return root.getChildren();
    }

    @Override
    @Cacheable(value = "category",key = "#slug")
    public Category getBySlug(String slug) {
        Assert.notNull(slug,"category slug must not be null");
        return categoryRepository.getBySlug(slug).orElse(null);
    }

    @Override
    public Category getBySlugOfNonNull(String slug) {
        Assert.notNull(slug,"category slug must not be null");
        return categoryRepository.getBySlug(slug)
                .orElseThrow(()->new NotFoundException("category with slug ="+slug+" was not found.")
                .setError(slug));
    }

    @Override
    public Category getByName(String name) {
        Assert.notNull(name,"category name must not be null.");
        return categoryRepository.getByName(name).orElse(null);
    }

    @Override
    @Transactional
    public void removeCategoryAndRelationById(Integer categoryId) {
        removeById(categoryId);
        //TODO remove category and post relationship
    }

    @Override
    public CategoryDTO convertTo(Category category) {
        Assert.notNull(category,"category must not be null.");
        return new CategoryDTO().convertFrom(category);
    }

    @Override
    public List<CategoryDTO> convertTo(Collection<Category> categories) {
        if(CollectionUtils.isEmpty(categories)){
            return Collections.emptyList();
        }
        return categories.stream()
                .map(this::convertTo)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "category")
    public List<Category> navigation() {
        return categoryRepository.findAllByAllowNavOrderBySortNumAsc(1);
    }

    @Override
    public List<Category> findByParent(Integer parentId) {
        return categoryRepository.findAllByParentIdOrderBySortNumDesc(parentId);
    }

    @NonNull
    private CategoryVO getRoot(){
        CategoryVO vo = new CategoryVO();
        vo.setId(0);
        vo.setChildren(new LinkedList<>());
        vo.setParentId(-1);
        return vo;
    }

    /**
     * 初始化树
     * @param root          root节点
     * @param categories    category集合
     */
    private void initNodes(CategoryVO root,List<Category> categories){
        Assert.notNull(root,"root category must not be null");
        if(CollectionUtils.isEmpty(categories)){
            return;
        }
        List<Category> children = categories.stream()
                .filter(category -> Objects.equals(root.getId(),category.getParentId()))
                .collect(Collectors.toList());
        children.forEach(category -> {
            CategoryVO node = new CategoryVO().convertFrom(category);
            if(CollectionUtils.isEmpty(root.getChildren())){
                root.setChildren(new LinkedList<>());
            }
            root.getChildren().add(node);
        });
        categories.removeAll(children);
        //递归设置子节点
        if(!CollectionUtils.isEmpty(root.getChildren())){
            root.getChildren().forEach(child->initNodes(child,categories));
        }
    }
}
