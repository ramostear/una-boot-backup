package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.common.util.ServiceUtils;
import com.ramostear.unaboot.domain.dto.CategoryPostCountDTO;
import com.ramostear.unaboot.domain.entity.Category;
import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.entity.PostCategory;
import com.ramostear.unaboot.domain.vo.CategoryPostCountVO;
import com.ramostear.unaboot.repository.CategoryRepository;
import com.ramostear.unaboot.repository.PostCategoryRepository;
import com.ramostear.unaboot.repository.PostRepository;
import com.ramostear.unaboot.service.PostCategoryService;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ramostear on 2019/11/14 0014.
 */
@Slf4j
@Service("postCategoryService")
public class PostCategoryServiceImpl extends UnaService<PostCategory,Integer> implements PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;

    private final CategoryRepository categoryRepository;

    private final PostRepository postRepository;


    public PostCategoryServiceImpl(PostCategoryRepository postCategoryRepository,
                                   CategoryRepository categoryRepository,
                                   PostRepository postRepository) {
        super(postCategoryRepository);
        this.postCategoryRepository = postCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Category> findCategoryByPostId(Integer postId) {
        Assert.notNull(postId,"post id must not be null");
        Integer categoryId = postCategoryRepository.findCategoryIdByPostId(postId);
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Map<Integer, List<Category>> convertCategoryToMapByPostIds(Collection<Integer> postIds) {
        if(CollectionUtils.isEmpty(postIds)){
            return Collections.emptyMap();
        }
        List<PostCategory> postCategories = postCategoryRepository.findAllByPostIdIn(postIds);

        Set<Integer> categoryIds = ServiceUtils.fetchProperty(postCategories,PostCategory::getCategoryId);

        List<Category> categories = categoryRepository.findAllById(categoryIds);

        Map<Integer,Category> categoryMap = ServiceUtils.convertTo(categories,Category::getId);

        Map<Integer,List<Category>> result = new HashMap<>();

        postCategories.forEach(postCategory -> result.computeIfAbsent(postCategory.getPostId(),postId->new LinkedList<>()).add(categoryMap.get(postCategory.getCategoryId())));
        return result;
    }

    @Override
    public List<Post> findAllPostByCategoryId(Integer categoryId) {
        Assert.notNull(categoryId,"category id must not be null");
        Set<Integer> postIds = postCategoryRepository.findPostIdsByCategoryId(categoryId);
        return postRepository.findAllById(postIds);
    }

    @Override
    public List<Post> findAllPostByCategoryIdAndPostStatus(Integer categoryId, Integer status) {
        Assert.notNull(categoryId,"category id must not be null");
        Assert.notNull(status,"post status must not be null");
        Set<Integer> postIds = postCategoryRepository.findPostIdsByCategoryIdAndPostStatus(categoryId,status);
        return postRepository.findAllById(postIds);
    }

    @Override
    public Page<Post> pagePostByCategoryId(Integer categoryId, Pageable pageable) {
        Assert.notNull(categoryId,"category id must not be null");
        Assert.notNull(pageable,"pageable must not be null");
        Set<Integer> postIds = postCategoryRepository.findPostIdsByCategoryIdAndPostStatus(categoryId,1);
        return postRepository.findAllByIdIn(postIds,pageable);
    }

    @Override
    @Transactional
    public PostCategory mergeOrCreated(Integer postId, Integer categoryId) {
        Assert.notNull(postId,"post id must not be null");
        Assert.notNull(categoryId,"category id must not be null");

        PostCategory postCategory = postCategoryRepository.findByPostId(postId);
        if(postCategory == null){
            postCategory = new PostCategory();
            postCategory.setPostId(postId);
            postCategory.setCategoryId(categoryId);
        }else{
            if(postCategory.getCategoryId().equals(categoryId)){
                postCategory.setCategoryId(categoryId);
            }
        }
        postCategoryRepository.save(postCategory);
        return postCategory;
    }

    @Override
    public PostCategory findByPostId(Integer postId) {
        Assert.notNull(postId,"post id must not be null");
        return postCategoryRepository.findByPostId(postId);
    }

    @Override
    public List<PostCategory> findAllByCategoryId(Integer categoryId) {
        Assert.notNull(categoryId,"category id must not be null");
        return postCategoryRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Integer findCategoryIdByPostId(Integer postId) {
        Assert.notNull(postId,"post id must not be null");
        return postCategoryRepository.findCategoryIdByPostId(postId);
    }

    @Override
    @Transactional
    public List<PostCategory> removeByPostId(Integer postId) {
        Assert.notNull(postId,"post id must not be null");
        return postCategoryRepository.deleteByPostId(postId);
    }

    @Override
    @Transactional
    public List<PostCategory> removeByCategoryId(Integer categoryId) {
        Assert.notNull(categoryId,"category id must not be null");
        return postCategoryRepository.deleteByCategoryId(categoryId);
    }

    @Override
    public List<CategoryPostCountDTO> findAllCategoryPostCountDTO(Sort sort) {
        Assert.notNull(sort,"sort must not be null");
        List<Category> categories = categoryRepository.findAll(sort);
        Map<Integer,Long> countMap = ServiceUtils.convertTo(
                postCategoryRepository.findCatetoryPostCountByPostStatus(1),
                CategoryPostCountVO::getCategoryId,CategoryPostCountVO::getPostCount);
        return categories.stream()
                .map(category -> {
                    CategoryPostCountDTO dto = new CategoryPostCountDTO().convertFrom(category);
                    dto.setPostCount(countMap.getOrDefault(category.getId(),0L));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public Post findTopPostByCategoryId(Integer categoryId) {
        Integer postId = postCategoryRepository.findTopPostIdByCategoryId(categoryId);
        if(postId != null){
            return postRepository.findById(postId).orElseGet(null);
        }
        return null;
    }
}
