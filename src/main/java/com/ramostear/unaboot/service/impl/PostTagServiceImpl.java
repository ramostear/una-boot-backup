package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.common.util.ServiceUtils;
import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.entity.PostTag;
import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.repository.PostRepository;
import com.ramostear.unaboot.repository.PostTagRepository;
import com.ramostear.unaboot.repository.TagRepository;
import com.ramostear.unaboot.service.PostTagService;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    @Cacheable(value = "tag",key = "#postId")
    public List<Tag> findAllTagByPostId(Integer postId) {
        Assert.notNull(postId,"post id must not be null");
        Set<Integer> tagIdSet = postTagRepository.findAllTagIdsByPostId(postId);
        if(CollectionUtils.isEmpty(tagIdSet)){
            return Collections.emptyList();
        }
        return tagRepository.findAllById(tagIdSet);
    }

    @Override
    public List<Post> findAllPostByTagId(Integer tagId) {
        Assert.notNull(tagId,"tag id must not be null");
        Set<Integer> postIdSet = postTagRepository.findAllPostIdsByTagId(tagId);
        if(CollectionUtils.isEmpty(postIdSet)){
            return Collections.emptyList();
        }
        return postRepository.findAllById(postIdSet);
    }

    @Override
    public List<Post> findAllPostByTagIdAndPostStatus(Integer tagId, Integer status) {
        Assert.notNull(tagId,"tag id must not be null");
        Assert.notNull(status,"post status must not be null");
        Set<Integer> postIdSet = postTagRepository.findAllPostIdsByTagIdAndPostStatus(tagId,status);
        if(CollectionUtils.isEmpty(postIdSet)){
            return Collections.emptyList();
        }
        return postRepository.findAllById(postIdSet);
    }

    @Override
    public Page<Post> pageAllPostByTagId(Integer tagId, Pageable pageable) {
        Assert.notNull(tagId,"tag id must not be null");
        Assert.notNull(pageable,"pageable must not be null");
        Set<Integer> postIdSet = postTagRepository.findAllPostIdsByTagId(tagId);
        if(CollectionUtils.isEmpty(postIdSet)){
            return Page.empty(pageable);
        }
        return postRepository.findAllByIdIn(postIdSet,pageable);
    }

    @Override
    public Page<Post> pageAllPostByTagIdAndPostStatus(Integer tagId, Integer status, Pageable pageable) {
        Assert.notNull(tagId,"tag id must not be null");
        Assert.notNull(status,"post status must not be null");
        Assert.notNull(pageable,"pageable must not be null");
        Set<Integer> postIdSet = postTagRepository.findAllPostIdsByTagIdAndPostStatus(tagId,status);
        if(CollectionUtils.isEmpty(postIdSet)){
            return Page.empty(pageable);
        }
        return postRepository.findAllByIdIn(postIdSet,pageable);
    }

    @Override
    @Transactional
    public List<PostTag> mergeOrCreatedIfAbsent(Integer postId, Set<Integer> tagIds) {
        Assert.notNull(postId,"post id must not be null");
        if(CollectionUtils.isEmpty(tagIds)){
            return Collections.emptyList();
        }

        List<PostTag> postTagList = tagIds.stream().map(tagId->{
            PostTag postTag = new PostTag();
            postTag.setPostId(postId);
            postTag.setTagId(tagId);
            return postTag;
        }).collect(Collectors.toList());

        List<PostTag> tempRemove = new LinkedList<>(),tempSave = new LinkedList<>();

        List<PostTag> alreadyExistsPostTagList = postTagRepository.findAllByPostId(postId);
        alreadyExistsPostTagList.forEach(postTag -> {
            if(!postTagList.contains(postTag)){
                tempRemove.add(postTag);
            }
        });
        postTagList.forEach(postTag -> {
            if(!alreadyExistsPostTagList.contains(postTag)){
                tempSave.add(postTag);
            }
        });
        super.removeAll(tempRemove);
        alreadyExistsPostTagList.removeAll(tempRemove);
        alreadyExistsPostTagList.addAll(super.createInBatch(tempSave));

        return alreadyExistsPostTagList;
    }

    @Override
    public List<PostTag> findAllByPostId(Integer postId) {
        Assert.notNull(postId,"post id must not be null");
        return postTagRepository.findAllByPostId(postId);
    }

    @Override
    public List<PostTag> findAllByTagId(Integer tagId) {
        Assert.notNull(tagId,"tag id must not be null");
        return postTagRepository.findAllByTagId(tagId);
    }

    @Override
    public Set<Integer> findAllTagIdByPostId(Integer postId) {
        Assert.notNull(postId,"post id must not be null");
        return postTagRepository.findAllTagIdsByPostId(postId);
    }

    @Override
    @Transactional
    public List<PostTag> removeByPostId(Integer postId) {
        Assert.notNull(postId,"post id must not be null");
        return postTagRepository.deleteByPostId(postId);
    }

    @Override
    @Transactional
    public List<PostTag> removeByTagId(Integer tagId) {
        Assert.notNull(tagId,"tag id must not be null");
        return postTagRepository.deleteByTagId(tagId);
    }

    @Override
    public Map<Integer, List<Tag>> convertTagToMapByPostId(Collection<Integer> postIds) {
        if(CollectionUtils.isEmpty(postIds)){
            return Collections.emptyMap();
        }
        List<PostTag> postTagList = postTagRepository.findAllByPostIdIn(postIds);

        Set<Integer> tagIdSet = ServiceUtils.fetchProperty(postTagList,PostTag::getTagId);

        List<Tag> tagList = tagRepository.findAllById(tagIdSet);

        Map<Integer,Tag> tagMap = ServiceUtils.convertTo(tagList,Tag::getId);

        Map<Integer,List<Tag>> tagListMap = new HashMap<>();

        postTagList.forEach(postTag -> tagListMap.computeIfAbsent(
                postTag.getPostId(),
                postId->new LinkedList<>()
            ).add(tagMap.get(postTag.getTagId()))
        );
        return tagListMap;
    }
}
