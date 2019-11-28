package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.common.util.ServiceUtils;
import com.ramostear.unaboot.domain.entity.Category;
import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.domain.vo.ArchiveVO;
import com.ramostear.unaboot.domain.vo.PostListVO;
import com.ramostear.unaboot.repository.PostRepository;
import com.ramostear.unaboot.service.ArchiveService;
import com.ramostear.unaboot.service.PostCategoryService;
import com.ramostear.unaboot.service.PostTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName ArchiveServiceImpl
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/16 0016 20:41
 * @Version 1.0
 **/
@Service("archiveService")
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostTagService postTagService;

    @Autowired
    private PostCategoryService postCategoryService;


    @Override
    @Cacheable(value = "archives")
    public List<ArchiveVO> archives() {
        List<Object[]> data = postRepository.generalArchives();
        List<ArchiveVO> archiveVOList = new ArrayList<>();
        data.forEach(item->{
            Object[] objects = item;
            ArchiveVO archiveVO = new ArchiveVO();
            archiveVO.setName(objects[0].toString());
            archiveVO.setCount(Long.parseLong(objects[1].toString()));
            archiveVOList.add(archiveVO);
        });
        return archiveVOList;
    }

    @Override
    @Cacheable(value = "archives",key = "#archiveName")
    public List<PostListVO> findAllPostByArchive(String archiveName) {
        if(StringUtils.isBlank(archiveName)){
            return Collections.emptyList();
        }
        return convertTo(postRepository.findAllPostByArchiveName(archiveName));
    }

    @NonNull
    private List<PostListVO> convertTo(@NonNull List<Post> postList){
        Set<Integer> postIdSet = ServiceUtils.fetchProperty(postList,Post::getId);
        Map<Integer,List<Tag>> postTagMap = postTagService.convertTagToMapByPostId(postIdSet);
        Map<Integer,List<Category>> postCategoryMap = postCategoryService.convertCategoryToMapByPostIds(postIdSet);
        return postList.stream()
                .map(post->{
                    PostListVO postListVO = new PostListVO().convertFrom(post);
                    postListVO.setTags(Optional.ofNullable(postTagMap.get(post.getId()))
                            .orElseGet(LinkedList::new)
                            .stream()
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList()));
                    postListVO.setCategory(Optional.ofNullable(postCategoryMap.get(post.getId()))
                            .orElseGet(LinkedList::new)
                            .stream()
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
                            .get(0));
                    postListVO.setChatCount(0L);
                    return postListVO;
                }).collect(Collectors.toList());
    }
}
