package com.ramostear.unaboot.repository;

import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.repository.support.UnaRepository;

/**
 * Created by Ramostear on 2019/11/14 0014.
 */
public interface TagRepository extends UnaRepository<Tag,Integer> {

    Tag findByNameAndSlug(String name,String slug);
}
