package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.vo.ArchiveVO;
import com.ramostear.unaboot.domain.vo.PostListVO;

import java.util.List;

public interface ArchiveService {

    /**
     * 获取文章归档
     * @return
     */
    List<ArchiveVO> archives();

    /**
     * 根据归档名称获取文章
     * @param archiveName
     * @return
     */
    List<PostListVO> findAllPostByArchive(String archiveName);
}
