package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.entity.PostTag;
import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.repository.PostRepository;
import com.ramostear.unaboot.repository.PostTagRepository;
import com.ramostear.unaboot.repository.TagRepository;
import com.ramostear.unaboot.service.PostTagService;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ramostear on 2019/11/14 0014.
 */
@Slf4j
@Service("postTagService")
public class PostTagServiceImpl extends UnaService<PostTag,Integer> implements PostTagService {

    private final TagRepository tagRepository;

    private final PostTagRepository postTagRepository;

    private final PostRepository postRepository;


    public PostTagServiceImpl(PostTagRepository postTagRepository,TagRepository tagRepository,PostRepository postRepository) {
        super(postTagRepository);
        this.postTagRepository = postTagRepository;
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAllTagByPostId(Integer postId) {
        return null;
    }

    @Override
    public List<Post> findAllPostByTagId(Integer tagId) {
        return null;
    }

    @Override
    public List<Post> findAllPostByTagIdAndPostStatus(Integer tagId, Integer status) {
        return null;
    }

    @Override
    public Page<Post> pageAllPostByTagId(Integer tagId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> pageAllPostByTagIdAndPostStatus(Integer tagId, Integer status, Pageable pageable) {
        return null;
    }

    @Override
    public List<PostTag> mergeOrCreatedIfAbsent(Integer postId, Set<Integer> tagIds) {
        return null;
    }

    @Override
    public List<PostTag> findAllByPostId(Integer postId) {
        return null;
    }

    @Override
    public List<PostTag> findAllByTagId(Integer tagId) {
        return null;
    }

    @Override
    public Set<Integer> findAllTagIdByPostId(Integer postId) {
        return null;
    }

    @Override
    public List<PostTag> removeByPostId(Integer postId) {
        return null;
    }

    @Override
    public List<PostTag> removeByTagId(Integer tagId) {
        return null;
    }

    @Override
    public Map<Integer, List<Tag>> convertTagToMapByPostId(Collection<Integer> postIds) {
        return null;
    }
}
