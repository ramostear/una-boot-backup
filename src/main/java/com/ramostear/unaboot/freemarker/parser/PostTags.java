package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PostTags
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/23 0023 1:47
 * @Version 1.0
 **/
@Service
public class PostTags extends TemplateDirective {

    @Autowired
    private PostTagService postTagService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer postId = handler.getInteger("postId");
        List<Tag> tagList = postTagService.findAllTagByPostId(postId);
        handler.put(RESULTS,tagList).render();
    }
}
