package com.ramostear.unaboot.repository;

import com.ramostear.unaboot.domain.entity.PostTag;
import com.ramostear.unaboot.repository.support.UnaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface PostTagRepository extends UnaRepository<PostTag,Integer> {

    @NonNull
    List<PostTag> findAllByPostId(@NonNull Integer postId);

    @NonNull
    @Query("select pt.tagId from PostTag as pt where pt.postId= :postId")
    Set<Integer> findAllTagIdsByPostId(@NonNull @Param("postId") Integer postId);

    @NonNull
    List<PostTag> findAllByTagId(@NonNull Integer tagId);

    @NonNull
    @Query("select pt.postId from PostTag as pt where pt.tagId= :tagId")
    Set<Integer> findAllPostIdsByTagId(@NonNull @Param("tagId")Integer tagId);

    @NonNull
    @Query("select pt.postId from PostTag as pt,Post as p where pt.tagId= :tagId and p.id = pt.postId and p.status = :status")
    Set<Integer> findAllPostIdsByTagIdAndPostStatus(@NonNull @Param("tagId")Integer tagId,@NonNull @Param("status")Integer status);

    @NonNull
    List<PostTag> findAllByPostIdIn(@NonNull Iterable<Integer> postIds);

    @NonNull
    List<PostTag> deleteByPostId(@NonNull Integer postId);

    @NonNull
    List<PostTag> deleteByTagId(@NonNull Integer tagId);

}
