package com.ramostear.unaboot.service.support;

import com.ramostear.unaboot.common.exception.NotFoundException;
import com.ramostear.unaboot.repository.support.UnaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName UnaService
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:38
 * @Version 1.0
 **/
@Slf4j
public abstract class UnaService<T,ID> implements IUnaService<T,ID> {
    private final String beanName;
    private final UnaRepository<T,ID> repository;

    public UnaService(UnaRepository repository){
        this.repository = repository;
        Class<T> clz = (Class<T>)fetchType(0);
        beanName = clz.getSimpleName();
    }
    private Type fetchType(int index){
        Assert.isTrue((index >= 0 && index <= 1),"type index must be between 0 to 1");
        return ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[index];
    }

    @Override
    public List<T> listAll() {
        return repository.findAll();
    }

    @Override
    public List<T> listAll(Sort sort) {
        Assert.notNull(sort,"sort must not be null");
        return repository.findAll(sort);
    }

    @Override
    public Page<T> listAll(Pageable pageable) {
        Assert.notNull(pageable,"page info must not be null");
        return repository.findAll(pageable);
    }

    @Override
    public List<T> listAllByIds(Collection<ID> ids) {
        return CollectionUtils.isEmpty(ids)? Collections.emptyList():repository.findAllById(ids);
    }

    @Override
    public List<T> listAllByIdsAndSort(Collection<ID> ids, Sort sort) {
        Assert.notNull(sort,"sort must not be null");
        return CollectionUtils.isEmpty(ids)?Collections.emptyList():repository.findAllByIdIn(ids,sort);
    }

    @Override
    public Optional<T> fetchById(ID id) {
        Assert.notNull(id,beanName+" id must not be null");
        return repository.findById(id);
    }

    @Override
    public T getById(ID id) {
        return fetchById(id).orElseThrow(()-> new NotFoundException(beanName+ " was not found or has been deleted"));
    }

    @Override
    public T getByIdNullable(ID id) {
        return fetchById(id).orElse(null);
    }

    @Override
    public boolean existsById(ID id) {
        Assert.notNull(id,beanName+" id must not be null");
        return repository.existsById(id);
    }

    @Override
    public void notNullById(ID id) {
        if(!existsById(id))
            throw new NotFoundException(beanName + " was not exists");
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public T create(T t) {
        Assert.notNull(t,beanName + " data must not be null");
        return repository.save(t);
    }

    @Override
    @Transactional
    public List<T> createInBatch(Collection<T> collection) {
        return CollectionUtils.isEmpty(collection)?Collections.emptyList():repository.saveAll(collection);
    }

    @Override
    @Transactional
    public T update(T t) {
        Assert.notNull(t,beanName + " data must not be null");
        return repository.saveAndFlush(t);
    }

    @Override
    @Transactional
    public List<T> updateInBatch(Collection<T> collection) {
        return CollectionUtils.isEmpty(collection)?Collections.emptyList():repository.saveAll(collection);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    @Transactional
    public T removeById(ID id) {
        T t = getById(id);
        remove(t);
        return t;
    }

    @Override
    @Transactional
    public T removeNullable(ID id) {
        return fetchById(id).map(t->{
            remove(t);
            return t;
        }).orElse(null);
    }

    @Override
    @Transactional
    public void remove(T t) {
        Assert.notNull(t,beanName+" data must not be null");
        repository.delete(t);
    }

    @Override
    @Transactional
    public void removeInBatch(Collection<ID> ids) {
        if(CollectionUtils.isEmpty(ids)){
            log.warn(beanName+ " id collection is empty");
            return;
        }
        repository.deleteByIdIn(ids);
    }

    @Override
    @Transactional
    public void removeAll(Collection<T> collection) {
        if(CollectionUtils.isEmpty(collection)){
            log.warn(beanName + "collections is empty");
            return;
        }
        repository.deleteInBatch(collection);
    }

    @Override
    @Transactional
    public void removeAll() {
        repository.deleteAll();
    }
}
