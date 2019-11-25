package com.ramostear.unaboot.repository;

import com.ramostear.unaboot.domain.entity.PostCategory;
import com.ramostear.unaboot.domain.vo.CategoryPostCountVO;
import com.ramostear.unaboot.repository.support.UnaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

public interface PostCategoryRepository extends UnaRepository<PostCategory,Integer> {

    @NonNull
    @Query("select pc.categoryId from PostCategory as pc where pc.postId= :postId")
    Integer findCategoryIdByPostId(@NonNull @Param("postId")Integer postId);

    @NonNull
    @Query("select pc.postId from PostCategory as pc where pc.categoryId= :categoryId")
    Set<Integer> findPostIdsByCategoryId(@NonNull @Param("categoryId")Integer categoryId);

    @NonNull
    @Query("select pc.postId from PostCategory pc,Post p where pc.categoryId= :categoryId and p.status= :status and pc.postId = p.id")
    Set<Integer> findPostIdsByCategoryIdAndPostStatus(@NonNull @Param("categoryId")Integer categoryId,
                                                      @NonNull @Param("status")Integer status);

    @NonNull
    List<PostCategory> findAllByPostIdIn(@NonNull Iterable<Integer>postIds);

    @NonNull
    PostCategory findByPostId(@NonNull Integer postId);

    @NonNull
    List<PostCategory> findAllByCategoryId(@NonNull Integer categoryId);

    @NonNull
    List<PostCategory> deleteByPostId(@NonNull Integer postId);

    @NonNull
    List<PostCategory> deleteByCategoryId(@NonNull Integer categoryId);

    @NonNull
    @Query("select new com.ramostear.unaboot.domain.vo.CategoryPostCountVO(count(pc.postId),pc.categoryId,c.name,c.slug) from PostCategory as pc,Category as c where pc.categoryId = c.id group by pc.categoryId")
    List<CategoryPostCountVO> findCatetoryPostCount();


    @NonNull
    @Query("select new com.ramostear.unaboot.domain.vo.CategoryPostCountVO(count(pc.postId),pc.categoryId,c.name,c.slug) from PostCategory as pc,Category as c,Post as p where pc.categoryId = c.id and pc.postId = p.id and p.status = :status group by pc.categoryId")
    List<CategoryPostCountVO> findCatetoryPostCountByPostStatus(@NonNull @Param("status") Integer status);

    @NonNull
    @Query(nativeQuery = true,value = "select p.id from posts as p,post_category as pc where pc.category_id = ?1 and pc.post_id = p.id and p.status =1 order by p.create_time asc limit 1")
    Integer findTopPostIdByCategoryId(@NonNull Integer categoryId);
}
