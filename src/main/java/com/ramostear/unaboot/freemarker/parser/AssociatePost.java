package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.dto.PostMiniDTO;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AssociatePost
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/16 0016 23:00
 * @Version 1.0
 **/
@Slf4j
@Service
public class AssociatePost extends TemplateDirective {

    @Autowired
    private PostService postService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer postId = handler.getInteger("id");
        Integer size = handler.getInteger("size",4);
        List<PostMiniDTO> postMiniDTOList = postService.associatePost(postId,size);
        handler.put(RESULTS,postMiniDTOList).render();
    }
}
