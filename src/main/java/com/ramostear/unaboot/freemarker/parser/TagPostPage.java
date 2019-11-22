package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.PostCategoryService;
import com.ramostear.unaboot.service.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @ClassName CategoryPostPage
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/23 0023 0:32
 * @Version 1.0
 **/
@Service
public class TagPostPage extends TemplateDirective {

    @Autowired
    private PostTagService postTagService;


    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer tagId = handler.getInteger("tagId");
        Integer offset = handler.getInteger("offset",1);
        Integer size = handler.getInteger("size",15);
        Page<Post> data = postTagService.pageAllPostByTagIdAndPostStatus(tagId,1,PageRequest.of(offset-1,size,new Sort(Sort.Direction.DESC,"createTime")));
        handler.put(RESULT,data).render();
    }
}
