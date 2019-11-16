package com.ramostear.unaboot.freemarker.parser.abs;

import com.ramostear.unaboot.common.UnaConst;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

import static com.ramostear.unaboot.common.util.FreemarkerModelUtils.*;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Slf4j
@Service
public abstract class BaseMethod extends ApplicationObjectSupport implements TemplateMethodModelEx {

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;


    @PostConstruct
    public void init() throws TemplateModelException{
        String className = this.getClass().getName();
        className = className.substring(className.lastIndexOf(".")+1);

        String beanName = StringUtils.uncapitalize(className);

        String tagName = UnaConst.RENDER_TAG_PREFIX+StringUtils.uncapitalize(className);

        log.info("render tag name :[{}]",tagName);
        freeMarkerConfigurer.getConfiguration()
                .setSharedVariable(tagName,this.getApplicationContext().getBean(beanName));
    }


    public String getString(List<TemplateModel> arguments,int index) throws TemplateModelException{
        return convertToString(getModel(arguments,index));
    }

    public Integer getInteger(List<TemplateModel> arguments,int index) throws TemplateModelException{
        return convertToInteger(getModel(arguments,index));
    }

    public Long getLong(List<TemplateModel> arguments,int index) throws TemplateModelException{
        return convertToLong(getModel(arguments,index));
    }

    public Date getDate(List<TemplateModel> arguments,int index) throws TemplateModelException{
        return convertToDate(getModel(arguments,index));
    }

    public Boolean getBoolean(List<TemplateModel> arguments,int index) throws TemplateModelException{
        return convertToBoolean(getModel(arguments,index));
    }


    public Double getDouble(List<TemplateModel>arguments,int index) throws TemplateModelException{
        return convertToDouble(getModel(arguments,index));
    }

    public TemplateHashModel getMap(List<TemplateModel>arguments, int index) throws TemplateModelException{
        return convertToMap(getModel(arguments,index));
    }


    public TemplateModel getModel(List<TemplateModel> arguments,int index){
        if(index < arguments.size()){
            return arguments.get(index);
        }
        return null;
    }

}
