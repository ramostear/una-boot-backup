package com.ramostear.unaboot.freemarker.shiro.abs;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
public abstract class RoleModel extends SecurityModel {

    String name(Map params){
        return getParam(params,"name");
    }

    protected abstract boolean showBody(String roleName);

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        boolean isShow = showBody(name(params));
        if(isShow){
            renderBody(env,body);
        }
    }
}
