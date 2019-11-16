package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.vo.PostListVO;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.ArchiveService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName ArchivePostList
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/16 0016 21:16
 * @Version 1.0
 **/
@Slf4j
@Service
public class ArchivePost extends TemplateDirective {

    @Autowired
    private ArchiveService archiveService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        String name = handler.getString("name");
        List<PostListVO> postListVOList = archiveService.findAllPostByArchive(name);
        handler.put(RESULTS,postListVOList).render();
    }
}
