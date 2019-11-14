package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.service.support.IUnaService;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ramostear on 2019/11/14 0014.
 */
public interface TagService extends IUnaService<Tag,Integer> {

    @NonNull
    boolean existBy(@NonNull String name,@NonNull String slug);

    @Transactional
    Tag createBy(@NonNull Tag tag);
}
