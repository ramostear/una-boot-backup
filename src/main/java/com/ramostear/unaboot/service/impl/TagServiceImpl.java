package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.repository.TagRepository;
import com.ramostear.unaboot.service.TagService;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ramostear on 2019/11/14 0014.
 */
@Slf4j
@Service("tagService")
public class TagServiceImpl extends UnaService<Tag,Integer> implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        super(tagRepository);
        this.tagRepository = tagRepository;
    }

    @Override
    public boolean existBy(String name, String slug) {
        Assert.notNull(name,"tag name must not be null");
        Assert.notNull(slug,"tag slug must not be null");
        return tagRepository.findByNameAndSlug(name,slug) != null;
    }

    @Override
    @Transactional
    public Tag createBy(Tag tag) {
        Assert.notNull(tag,"tag must not be null");
        boolean flag = existBy(tag.getName(),tag.getSlug());
        Assert.isTrue(!flag,"tag already exists");
        return tagRepository.save(tag);
    }
}
