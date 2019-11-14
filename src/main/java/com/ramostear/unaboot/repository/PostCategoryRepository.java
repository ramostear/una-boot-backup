package com.ramostear.unaboot.repository;

import com.ramostear.unaboot.domain.entity.PostCategory;
import com.ramostear.unaboot.domain.vo.CategoryPostCountVO;
import com.ramostear.unaboot.repository.support.UnaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface PostCategoryRepository extends UnaRepository<PostCategory,Integer> {

    @NonNull
    @Query("select pc.categoryId from PostCategory as pc where pc.postId= :postId")
    Set<Integer> findCategoryIdsByPostId(@NonNull @Param("postId")Integer postId);

    @NonNull
    @Query("select pc.postId from PostCategory as pc where pc.categoryId= :categoryId")
    Set<Integer> findPostIdsByCategoryId(@NonNull @Param("categoryId")Integer categoryId);

    @NonNull
    @Query("select pc.postId from PostCategory pc,Post p where pc.categoryId= :categoryId and p.status= :status and pc.postId = p.id")
    Set<Integer> findPostIdsByCategoryIdAndPostStatus(@NonNull @Param("categoryId")Integer categoryId,
                                                      @NonNull @Param("status")Integer status);

    @NonNull
    List<PostCategory> findAllByPostIdIn(@NonNull Iterator<Integer>postIds);

    @NonNull
    List<PostCategory> findAllByPostId(@NonNull Integer postId);

    @NonNull
    List<PostCategory> findAllByCategoryId(@NonNull Integer categoryId);

    @NonNull
    List<PostCategory> deleteByPostId(@NonNull Integer postId);

    @NonNull
    List<PostCategory> deleteByCategoryId(@NonNull Integer categoryId);

    @NonNull
    @Query("select new com.ramostear.unaboot.domain.vo.CategoryPostCountVO(count(pc.postId),pc.categoryId,c.name,c.slug) from PostCategory as pc,Category as c where pc.categoryId = c.id group by pc.categoryId")
    List<CategoryPostCountVO> findCatetoryPostCount();
}
