package com.ramostear.unaboot.freemarker.shiro.abs;

import com.ramostear.unaboot.common.UnaConst;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
public abstract class PermissionModel extends SecurityModel{


    String name(Map params){
        return getParam(params,"name");
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String permit = name(params);
        boolean isShow = showBody(permit);
        if(isShow){
            renderBody(env,body);
        }
    }

    @Override
    protected void verifyParams(Map params) throws TemplateModelException {
        String permission = name(params);
        if(StringUtils.isBlank(permission)){
            throw new TemplateModelException("the 'name' of tag attribute must not be null");
        }
    }

    protected boolean isPermitted(String permit){
        return getSubject() != null && (getSubject().hasRole(UnaConst.DEFAULT_ROLE_NAME) || getSubject().isPermitted(permit));
    }

    protected abstract boolean showBody(String permit);
}

