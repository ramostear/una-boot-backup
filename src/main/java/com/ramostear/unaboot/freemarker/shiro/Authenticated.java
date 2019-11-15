package com.ramostear.unaboot.freemarker.shiro;

import com.ramostear.unaboot.freemarker.shiro.abs.SecurityModel;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
public class Authenticated extends SecurityModel {
    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        if(getSubject() != null && getSubject().isAuthenticated()){
            renderBody(env,body);
        }
    }
}
