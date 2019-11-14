package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.dto.CategoryPostCountDTO;
import com.ramostear.unaboot.domain.entity.Category;
import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.entity.PostCategory;
import com.ramostear.unaboot.service.support.IUnaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Ramostear on 2019/11/14 0014.
 */
public interface PostCategoryService extends IUnaService<PostCategory,Integer> {

    @NonNull
    Optional<Category> findCategoryByPostId(@NonNull Integer postId);

    @NonNull
    Map<Integer,List<Category>> convertCategoryToMapByPostIds(@NonNull Collection<Integer> postIds);

    @NonNull
    List<Post> findAllPostByCategoryId(@NonNull Integer categoryId);

    @NonNull
    List<Post> findAllPostByCategoryIdAndPostStatus(@NonNull Integer categoryId,@NonNull Integer status);

    @NonNull
    Page<Post> pagePostByCategoryId(@NonNull Integer categoryId, @NonNull Pageable pageable);

    @NonNull
    PostCategory mergeOrCreated(@NonNull Integer postId,@NonNull Integer categoryId);

    @NonNull
    PostCategory findByPostId(@NonNull Integer postId);

    @NonNull
    List<PostCategory> findAllByCategoryId(@NonNull Integer categoryId);

    @NonNull
    Integer findCategoryIdByPostId(@NonNull Integer postId);

    @NonNull
    List<PostCategory> removeByPostId(@NonNull Integer postId);

    @NonNull
    List<PostCategory> removeByCategoryId(@NonNull Integer categoryId);

    @NonNull
    List<CategoryPostCountDTO> findAllCategoryPostCountDTO(@NonNull Sort sort);
}
