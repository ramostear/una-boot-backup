package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.entity.Category;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.CategoryService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Service
public class Navigate extends TemplateDirective {


    @Autowired
    private CategoryService categoryService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        List<Category> categoryList = categoryService.navigation();
        handler.put(RESULTS,categoryList).render();
    }
}
