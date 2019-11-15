package com.ramostear.unaboot.freemarker.parser.abs;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.common.util.NameUtils;
import freemarker.core.Environment;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Slf4j
@Service
public abstract class TemplateDirective extends ApplicationObjectSupport implements TemplateDirectiveModel {

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    protected static final String RESULT = "result";

    protected static final String RESULTS = "results";

    @PostConstruct
    public void init() throws TemplateModelException{
        String className = this.getClass().getName();
        className = className.substring(className.lastIndexOf(".")+1);
        String beanName = StringUtils.uncapitalize(className);
        String tagName = UnaConst.RENDER_TAG_PREFIX+ NameUtils.humpToUnderline(beanName);
        log.debug("tag name :[{}]",tagName);
        freeMarkerConfigurer.getConfiguration()
                .setSharedVariable(tagName,this.getApplicationContext().getBean(beanName));
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        try {
            execute(new DirectiveHandler(env, params, loopVars, body));
        }catch (TemplateException ex){
            throw ex;
        }
    }

    abstract public String getName();

    abstract public void execute(DirectiveHandler handler) throws TemplateException, IOException;

}
