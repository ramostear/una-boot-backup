package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.dto.PostMiniDTO;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName Hottest
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/17 0017 3:05
 * @Version 1.0
 **/
@Service
public class Hottest extends TemplateDirective {

    @Autowired
    private PostService postService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer size = handler.getInteger("size",5);
        List<PostMiniDTO> postMiniDTOList = postService.findHottestPost(size);
        handler.put(RESULTS,postMiniDTOList).render();
    }
}
