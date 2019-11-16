package com.ramostear.unaboot.repository;

import com.ramostear.unaboot.domain.dto.PostMiniDTO;
import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.repository.support.UnaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
public interface PostRepository extends UnaRepository<Post,Integer>, JpaSpecificationExecutor<Post> {

    Post findByStatusAndSlug(Integer status,String slug);

    Post findBySlug(String slug);

    /**
     * 获取文章归档
     * @return
     */
    @Query(nativeQuery = true,value = "select DATE_FORMAT(p.create_time,'%Y年%m月') as name,COUNT(p.id) as pcount from posts as p group by name order by name desc")
    List<Object[]> generalArchives();

    /**
     * 获取归档下的文章列表
     * @param name      归档名称
     * @return          Posts
     */
    @Query(nativeQuery = true,value = "select p.* from posts as p where date_format(p.create_time,'%Y年%m月')=?1 and p.status=1 order by p.create_time desc")
    List<Post> findAllPostByArchiveName(String name);

    /**
     * 获取特定栏目下当前文章的上一篇内容
     * @param currentPostId     当前文章编号
     * @param categoryId        当前文章所属的栏目编号
     * @return                  Object[]
     */
    @Query(nativeQuery = true,value="select p.id,p.title,p.slug from posts as p ,post_category as pc where p.id <?1 and p.status=1 and p.id=pc.post_id and pc.category_id=?2 order by p.id desc limit 1")
    Object[] beforePost(Integer currentPostId,Integer categoryId);

    /**
     * 获取特定栏目下当前文章的下一篇内容
     * @param currentPostId     当前文章编号
     * @param categoryId        当前文章所属栏目编号
     * @return                  Object[]
     */
    @Query(nativeQuery = true,value="select p.id,p.title,p.slug from posts as p ,post_category as pc where p.id >?1 and p.status=1 and p.id=pc.post_id and pc.category_id=?2 order by p.id asc limit 1")
    Object[] afterPost(Integer currentPostId,Integer categoryId);

    /**
     * 查询相关联的文章
     * @param tagIds
     * @param size
     * @return
     */
    @Query(nativeQuery = true,value="select p.* from posts as p ,tags as t,post_tag as pt where p.status = 1 and t.id in (?1) and p.id=pt.post_id and t.id=pt.tag_id order by RAND() limit ?2")
    List<Post> associatePostByTags(List<Integer> tagIds,int size);

    @Query(nativeQuery = true,value="select p.* from posts as p where p.status =1 order by p.create_time desc limit 0,?1")
    List<Post> findNewestPosts(Integer size);

    @Query(nativeQuery = true,value = "select p.* from posts as p where p.status = 1 order by p.visits desc limit ?1")
    List<Post> findHottestPosts(Integer size);
}
