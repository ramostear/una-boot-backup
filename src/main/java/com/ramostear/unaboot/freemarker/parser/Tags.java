package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.entity.Tag;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.TagService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Slf4j
@Service
public class Tags extends TemplateDirective {

    @Autowired
    private TagService tagService;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        List<Tag> tagList = tagService.listAll(new Sort(Sort.Direction.DESC,"createTime"));
        handler.put(RESULTS,tagList).render();
    }
}
