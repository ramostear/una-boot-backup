package com.ramostear.unaboot.repository;

import com.ramostear.unaboot.domain.entity.PostTag;
import com.ramostear.unaboot.repository.support.UnaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PostTagRepository extends UnaRepository<PostTag,Integer> {

    @NonNull
    List<PostTag> findAllByPostId(@NonNull Integer postId);
}
