package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.param.PostQuery;
import com.ramostear.unaboot.domain.vo.PostListVO;
import com.ramostear.unaboot.domain.vo.PostVO;
import com.ramostear.unaboot.service.support.IUnaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

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
}
