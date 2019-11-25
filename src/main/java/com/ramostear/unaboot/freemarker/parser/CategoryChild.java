package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.entity.Category;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Ramostear on 2019/11/25 0025.
 */
@Service
public class CategoryChild extends TemplateDirective {

    @Autowired
    private CategoryService categoryService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer parent = handler.getInteger("parent");
        if(parent != null && parent > 0){
            List<Category> categories = categoryService.findByParent(parent);
            if(CollectionUtils.isEmpty(categories)){
                handler.put(RESULTS,null).render();
            }else{
                handler.put("children",categories).render();
            }
        }
    }
}
