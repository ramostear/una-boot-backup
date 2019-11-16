package com.ramostear.unaboot.freemarker.parser;

import com.ramostear.unaboot.domain.entity.Link;
import com.ramostear.unaboot.freemarker.parser.abs.DirectiveHandler;
import com.ramostear.unaboot.freemarker.parser.abs.TemplateDirective;
import com.ramostear.unaboot.service.LinkService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName Links
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/16 0016 17:23
 * @Version 1.0
 **/
@Service
public class Links extends TemplateDirective {

    @Autowired
    private LinkService linkService;

    @Override
    public String getName() {
        return null;
    }
    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        List<Link> linkList = linkService.listAll(new Sort(Sort.Direction.DESC,"sortNum"));
        handler.put(RESULTS,linkList).render();
    }
}
