package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.dto.PostMiniDTO;
import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.param.PostQuery;
import com.ramostear.unaboot.domain.vo.PostListVO;
import com.ramostear.unaboot.domain.vo.PostVO;
import com.ramostear.unaboot.service.support.IUnaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Ramostear on 2019/11/14 0014.
 */
public interface PostService extends IUnaService<Post,Integer> {

    @NonNull
    Page<Post> pageBy(@NonNull PostQuery postQuery, @NonNull Pageable pageable);

    @NonNull
    Page<Post> pageBy(@NonNull String keyword,@NonNull Pageable pageable);

    @NonNull
    @Transactional
    PostVO createBy(@NonNull Post post, Set<Integer> tagIds,Integer categoryId,boolean autoSave);

    @NonNull
    @Transactional
    PostVO updateBy(@NonNull Post post,Set<Integer> tagIds,Integer categoryId,boolean autoSave);

    @NonNull
    Post getBy(@NonNull Integer status,@NonNull String slug);

    boolean existBy(@NonNull String slug);

    @NonNull
    PostVO convertToPostVO(@NonNull Post post);

    @NonNull
    Page<PostListVO> convertToPostListVO(@NonNull Page<Post> postPage);

    PostMiniDTO beforePost(Integer currentPostId,Integer categoryId);

    PostMiniDTO afterPost(Integer currentPostId,Integer categoryId);

    /**
     * 获取与当前文章相关的文章,使用标签（tag）作为相关联因子
     * @param postId        当前文章编号
     * @param size          数量
     * @return              List<PostMiniDTO>
     */
    List<PostMiniDTO> associatePost(Integer postId,Integer size);

    /**
     * 获取最新的文章
     * @param size      查询数量
     * @return
     */
    List<PostMiniDTO> findNewestPost(Integer size);

    /**
     * 根据阅读数获取醉热门的文章
     * @param size      查询数量
     * @return          List<PostMiniDTO>
     */
    List<PostMiniDTO> findHottestPost(Integer size);

    Post findBySlug(String slug);

    Post identityVisits(Integer id);
}
