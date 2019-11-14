package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.param.PostQuery;
import com.ramostear.unaboot.domain.vo.PostListVO;
import com.ramostear.unaboot.domain.vo.PostVO;
import com.ramostear.unaboot.repository.PostRepository;
import com.ramostear.unaboot.service.*;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Ramostear on 2019/11/14 0014.
 */
@Slf4j
@Service("postService")
public class PostServiceImpl extends UnaService<Post,Integer> implements PostService {

    private final PostRepository postRepository;

    private final TagService tagService;

    private final CategoryService categoryService;

    private final PostTagService postTagService;

    private final PostCategoryService postCategoryService;

    public PostServiceImpl(PostRepository postRepository,TagService tagService,
                           CategoryService categoryService,PostTagService postTagService,
                           PostCategoryService postCategoryService) {
        super(postRepository);
        this.postRepository = postRepository;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.postTagService =  postTagService;
        this.postCategoryService = postCategoryService;
    }

    @Override
    public Page<Post> pageBy(PostQuery postQuery, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> pageBy(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public PostVO createBy(Post post, Set<Integer> tagIds, Integer categoryId, boolean autoSave) {
        return null;
    }

    @Override
    public PostVO updateBy(Post post, Set<Integer> tagIds, Integer categoryId, boolean autoSave) {
        return null;
    }

    @Override
    public Post getBy(Integer status, String slug) {
        return null;
    }

    @Override
    public boolean existBy(String slug) {
        return false;
    }

    @Override
    public PostVO converToPostVO(Post post) {
        return null;
    }

    @Override
    public Page<PostListVO> convertToPostListVO(Page<Post> postPage) {
        return null;
    }
}
