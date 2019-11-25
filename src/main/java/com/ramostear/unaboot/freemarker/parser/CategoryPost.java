package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.entity.Post;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.PostCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ramostear on 2019/11/25 0025.
 */
@Service
@Slf4j
public class CategoryPost extends TemplateDirective {

    @Autowired
    private PostCategoryService postCategoryService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer category = handler.getInteger("category");
        if(category != null && category > 0){
            List<Post> posts = postCategoryService.findAllPostByCategoryIdAndPostStatus(category,1);
            if(CollectionUtils.isEmpty(posts)){
                handler.put("posts",null).render();
            }else{
                List<Post>result = posts.stream()
                        .sorted(Comparator.comparing(Post::getId))
                        .collect(Collectors.toList());
                handler.put("posts",result).render();
            }
        }
    }
}
