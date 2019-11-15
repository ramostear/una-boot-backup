package com.ramostear.unaboot.freemarker.shiro.abs;

import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
public abstract class SecurityModel implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

    }

    public abstract void render(Environment env,Map params,TemplateDirectiveBody body) throws IOException,TemplateException;

    protected String getParam(Map params,String name){
        Object value = params.get(name);
        if(value instanceof SimpleScalar){
            return ((SimpleScalar)value).getAsString();
        }
        return null;
    }

    protected Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    protected void verifyParams(Map params) throws TemplateModelException{}

    protected void renderBody(Environment env,TemplateDirectiveBody body) throws IOException,TemplateException{
        if(body != null){
            body.render(env.getOut());
        }
    }
}
