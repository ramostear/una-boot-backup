package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.repository.TagRepository;
import com.ramostear.unaboot.service.TagService;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return false;
    }

    @Override
    public Tag createBy(Tag tag) {
        return null;
    }
}
