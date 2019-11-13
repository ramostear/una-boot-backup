package com.ramostear.unaboot.repository;

import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.repository.support.UnaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
public interface PostRepository extends UnaRepository<Post,Integer>, JpaSpecificationExecutor<Post> {

    Post findByStatusAndSlug(Integer status,String slug);

    Post findBySlug(String slug);
}
