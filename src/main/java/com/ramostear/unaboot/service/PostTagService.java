package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.entity.PostTag;
import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.service.support.IUnaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ramostear on 2019/11/14 0014.
 */
public interface PostTagService extends IUnaService<PostTag,Integer> {

    @NonNull
    List<Tag> findAllTagByPostId(@NonNull Integer postId);

    @NonNull
    List<Post> findAllPostByTagId(@NonNull Integer tagId);

    @NonNull
    List<Post> findAllPostByTagIdAndPostStatus(@NonNull Integer tagId,@NonNull Integer status);

    @NonNull
    Page<Post> pageAllPostByTagId(@NonNull Integer tagId,@NonNull Pageable pageable);

    @NonNull
    Page<Post> pageAllPostByTagIdAndPostStatus(@NonNull Integer tagId,@NonNull Integer status,@NonNull Pageable pageable);

    @NonNull
    List<PostTag> mergeOrCreatedIfAbsent(@NonNull Integer postId, @NonNull Set<Integer> tagIds);

    @NonNull
    List<PostTag> findAllByPostId(@NonNull Integer postId);

    @NonNull
    List<PostTag> findAllByTagId(@NonNull Integer tagId);

    @NonNull
    Set<Integer> findAllTagIdByPostId(@NonNull Integer postId);

    @NonNull
    @Transactional
    List<PostTag> removeByPostId(@NonNull Integer postId);

    @NonNull
    @Transactional
    List<PostTag> removeByTagId(@NonNull Integer tagId);

    @NonNull
    Map<Integer,List<Tag>> convertTagToMapByPostId(@NonNull Collection<Integer> postIds);

}
