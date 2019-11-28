package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.common.util.DateUtils;
import com.ramostear.unaboot.common.util.ServiceUtils;
import com.ramostear.unaboot.domain.dto.PostMiniDTO;
import com.ramostear.unaboot.domain.entity.*;
import com.ramostear.unaboot.domain.param.PostQuery;
import com.ramostear.unaboot.domain.vo.PostListVO;
import com.ramostear.unaboot.domain.vo.PostVO;
import com.ramostear.unaboot.repository.PostRepository;
import com.ramostear.unaboot.service.*;
import com.ramostear.unaboot.service.support.UnaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        Assert.notNull(postQuery,"query param must not be null");
        Assert.notNull(pageable,"pageable must not be null");
        return postRepository.findAll(buildSpecByQuery(postQuery),pageable);
    }

    @Override
    @Cacheable(value = "search",key = "#postQuery.keyword+'_'+#pageable.getOffset()")
    public Page<Post> search(PostQuery postQuery, Pageable pageable) {
        Assert.notNull(postQuery,"query param must not be null");
        Assert.notNull(pageable,"pageable must not be null");
        return postRepository.findAll(buildSpecByQuery(postQuery),pageable);
    }

    @Override
    public Page<Post> pageBy(String keyword, Pageable pageable) {
        Assert.notNull(keyword,"query param must not be null");
        Assert.notNull(pageable,"pageable must not be null");
        PostQuery postQuery = new PostQuery();
        postQuery.setKeyword(keyword);
        postQuery.setStatus(1);
        return postRepository.findAll(buildSpecByQuery(postQuery),pageable);
    }

    @Override
    @Transactional
    public PostVO createBy(Post post, Set<Integer> tagIds, Integer categoryId, boolean autoSave) {
        if (autoSave) {
            post.setStatus(1);
        } else {
            post.setStatus(0);
        }
        return createOrUpdate(post,tagIds,categoryId);
    }

    @Override
    @Transactional
    public PostVO updateBy(Post post, Set<Integer> tagIds, Integer categoryId, boolean autoSave) {
        if(autoSave){
            post.setStatus(1);
        }else{
            post.setStatus(0);
        }
        post.setUpdateTime(DateUtils.now());
        return createOrUpdate(post,tagIds,categoryId);
    }

    @Override
    public Post getBy(Integer status, String slug) {
        Assert.notNull(status,"post status must not be null");
        Assert.notNull(slug,"post slug must not be null");
        return postRepository.findByStatusAndSlug(status,slug);
    }

    @Override
    public boolean existBy(String slug) {
        Assert.notNull(slug,"post slug must not be null");
        return postRepository.findBySlug(slug) != null;
    }

    @Override
    public PostVO convertToPostVO(Post post) {
        Assert.notNull(post,"post must not be null");
        return convertTo(
                post,
                ()->postTagService.findAllTagIdByPostId(post.getId()),
                postCategoryService.findCategoryIdByPostId(post.getId())
        );
    }

    @Override
    public Page<PostListVO> convertToPostListVO(Page<Post> postPage) {
        Assert.notNull(postPage,"post page data must not be null");
        List<Post> posts = postPage.getContent();

        Set<Integer> postIds = ServiceUtils.fetchProperty(posts,Post::getId);
        Map<Integer,List<Tag>> tagListMap = postTagService.convertTagToMapByPostId(postIds);
        Map<Integer,List<Category>> categoryListMap = postCategoryService.convertCategoryToMapByPostIds(postIds);

        return postPage.map(post -> {
            PostListVO postListVO = new PostListVO().convertFrom(post);
            //设置文章标签
            Optional.ofNullable(tagListMap.get(post.getId())).orElseGet(LinkedList::new);
            postListVO.setTags(Optional.ofNullable(tagListMap.get(post.getId()))
                    .orElseGet(LinkedList::new)
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));

            //设置文章栏目
            postListVO.setCategory(Optional.ofNullable(categoryListMap.get(post.getId()))
                    .orElseGet(LinkedList::new)
                    .stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
                    .get(0));
            postListVO.setChatCount(0L);
            return postListVO;
        });
    }

    @Override
    public PostMiniDTO beforePost(Integer currentPostId, Integer categoryId) {
        if(currentPostId == null || categoryId == null){
            return null;
        }
        List<Object[]> data = postRepository.beforePost(currentPostId,categoryId);
        return convertToOne(data);
    }

    @Override
    public PostMiniDTO afterPost(Integer currentPostId, Integer categoryId) {
        if(currentPostId == null || categoryId == null){
            return null;
        }
        List<Object[]> data=postRepository.afterPost(currentPostId,categoryId);
        return convertToOne(data);
    }

    @Override
    public List<PostMiniDTO> associatePost(Integer postId, Integer size) {
        Set<Integer> tagIdSet = postTagService.findAllTagIdByPostId(postId);
        List<Post> postList = postRepository.associatePostByTags(new ArrayList<>(tagIdSet),postId,size);
        return convertTo(postList);
    }

    @Override
    public List<PostMiniDTO> findNewestPost(Integer size) {
        List<Post> postList = postRepository.findNewestPosts(size);
        return convertTo(postList);
    }

    @Override
    public List<PostMiniDTO> findHottestPost(Integer size) {
        List<Post> postList = postRepository.findHottestPosts(size);
        return convertTo(postList);
    }

    @Override
    @Cacheable(value = "posts",key = "#slug")
    public Post findBySlug(String slug) {
        if(StringUtils.isBlank(slug)){
            return null;
        }
        return postRepository.findBySlug(slug);
    }

    @Transactional
    @Override
    public Post identityVisits(Integer id) {
        Optional<Post> optional = postRepository.findById(id);
        if(optional.isPresent()){
            Post post = optional.get();
            post.setVisits(post.getVisits()+1);
            return postRepository.save(post);
        }
        return null;
    }

    @Override
    public Post removeById(Integer postId) {
        Assert.notNull(postId,"post id must not be null");
        log.debug("removing post:{}",postId);
        List<PostTag> postTagList = postTagService.removeByPostId(postId);
        log.debug("removed post tags:[{}]",postTagList);

        List<PostCategory> postCategories = postCategoryService.removeByPostId(postId);
        log.debug("removed post category:[{}]",postCategories);
        return super.removeById(postId);
    }

    /**
     * 创建多条件查询语句
     * @param postQuery     查询条件
     * @return              Specification<POST>
     */
    @NonNull
    private Specification<Post> buildSpecByQuery(@NonNull PostQuery postQuery){
        Assert.notNull(postQuery,"query param must not be null");
        return (Specification<Post>)(root,query,builder)->{
            List<Predicate> predicates = new LinkedList<>();
            if(postQuery.getStatus() != null){
                predicates.add(builder.equal(root.get("status"),postQuery.getStatus()));
            }
            if(postQuery.getCategoryId() != null){
                Subquery<Post> postSubquery = query.subquery(Post.class);
                Root<PostCategory> postCategoryRoot = postSubquery.from(PostCategory.class);
                postSubquery.select(postCategoryRoot.get("postId"));
                postSubquery.where(
                        builder.equal(root.get("id"),postCategoryRoot.get("postId")),
                        builder.equal(postCategoryRoot.get("categoryId"),postQuery.getCategoryId())
                );
                predicates.add(builder.exists(postSubquery));
            }
            if(StringUtils.isNotBlank(postQuery.getKeyword())){
                String condition = String.format("%%%s%%",StringUtils.strip(postQuery.getKeyword()));
                Predicate titleLike = builder.like(root.get("title"),condition);
                Predicate summaryLike = builder.like(root.get("summary"),condition);
                predicates.add(builder.or(titleLike,summaryLike));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }

    /**
     * 新增或更新持久化对象
     * @param post          文章
     * @param tagIds        标签
     * @param categoryId    栏目
     * @return              PostVO
     */
    private PostVO createOrUpdate(@NonNull Post post,Set<Integer> tagIds,Integer categoryId){
        Assert.notNull(post,"post must not be null");
        Category category = categoryService.getById(categoryId);
        Assert.notNull(category,"category must not be null");
        super.create(post);

        List<Tag> tags = tagService.listAllByIds(tagIds);

        List<PostTag> postTags = postTagService.mergeOrCreatedIfAbsent(post.getId(), ServiceUtils.fetchProperty(tags,Tag::getId));
        log.debug("create post tags : [{}]",postTags);

        PostCategory postCategory = postCategoryService.mergeOrCreated(post.getId(),category.getId());
        log.debug("create post category relationship : [{}]",postCategory);

        return convertTo(post,()->ServiceUtils.fetchProperty(postTags,PostTag::getId),category.getId());
    }

    /**
     * 将Post持久化对象转换为VO对象
     * @param post                  post持久化对象
     * @param tagIdSetSupplier      标签
     * @param categoryId            栏目
     * @return                      postVO
     */
    @NonNull
    private PostVO convertTo(@NonNull Post post, @NonNull Supplier<Set<Integer>> tagIdSetSupplier,@NonNull Integer categoryId){
        Assert.notNull(post,"post must not be null");
        Assert.notNull(categoryId,"category id must not be null");
        PostVO postVO = new PostVO().convertFrom(post);
        postVO.setTagIds(tagIdSetSupplier == null? Collections.emptySet():tagIdSetSupplier.get());
        postVO.setCategoryId(categoryId);
        return postVO;
    }

    private PostMiniDTO convertToOne(List<Object[]> data){
        PostMiniDTO miniDTO = new PostMiniDTO();
        if(data != null && data.size()>0){
            Object[] objects = data.get(0);
            Integer id = Integer.parseInt(objects[0].toString());
            String title = objects[1].toString();
            String slug = objects[2].toString();
            miniDTO.setId(id);
            miniDTO.setTitle(title);
            miniDTO.setSlug(slug);
            return miniDTO;
        }else{
            return null;
        }
    }

    private List<PostMiniDTO> convertTo(List<Post> postList){
        return postList.stream().map(post->{
            PostMiniDTO postMiniDTO = new PostMiniDTO().convertFrom(post);
            return postMiniDTO;
        }).collect(Collectors.toList());
    }

}
