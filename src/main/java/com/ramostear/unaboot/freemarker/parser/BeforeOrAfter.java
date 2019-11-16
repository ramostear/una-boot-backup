package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.dto.PostMiniDTO;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.PostService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @ClassName BeforeOrAfter
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/16 0016 21:24
 * @Version 1.0
 **/
@Slf4j
@Service
public class BeforeOrAfter extends TemplateDirective {

    private static final String BEFORE = "before";
    private static final String AFTER = "after";

    @Autowired
    private PostService postService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        Integer currentPostId = handler.getInteger("current");
        Integer categoryId = handler.getInteger("category");
        String type = handler.getString("type");
        PostMiniDTO miniDTO;
        if(type.equalsIgnoreCase(BEFORE)){
            miniDTO = postService.beforePost(currentPostId,categoryId);
        }else if(type.equalsIgnoreCase(AFTER)){
            miniDTO = postService.afterPost(currentPostId,categoryId);
        }else{
            miniDTO = null;
        }
        handler.put(RESULT,miniDTO).render();
    }
}
