package com.ramostear.unaboot.freemarker.shiro;

import com.ramostear.unaboot.freemarker.shiro.abs.SecurityModel;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
public class Principal extends SecurityModel {
    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String result = null;
        if(getSubject() != null){
            Object principal;
            if(getType(params) == null){
                principal = getSubject().getPrincipal();
            }else{
                principal = getPrincipalFromClassName(params);
            }
            if(principal != null){
                String property = getProperty(params);
                if(property == null){
                    result = principal.toString();
                }else{
                    result = getPrincipalProperty(principal,property);
                }
            }
        }
        if(result != null){
           try{
               env.getOut().write(result);
           } catch (IOException ex){
               throw new TemplateException("Error writing["+result+"] to freemarker",ex,env);
           }
        }
    }

    String getType(Map params) {
        return getParam(params, "type");
    }

    String getProperty(Map params) {
        return getParam(params, "property");
    }

    @SuppressWarnings("unchecked")
    Object getPrincipalFromClassName(Map params) {
        String type = getType(params);

        try {
            Class cls = Class.forName(type);

            return getSubject().getPrincipals().oneByType(cls);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    String getPrincipalProperty(Object principal, String property) throws TemplateModelException {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(principal.getClass());

            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                if (propertyDescriptor.getName().equals(property)) {
                    Object value = propertyDescriptor.getReadMethod().invoke(principal, (Object[]) null);

                    return String.valueOf(value);
                }
            }

            throw new TemplateModelException("Property [" + property + "] not found in principal of type ["
                    + principal.getClass().getName() + "]");
        } catch (Exception ex) {
            throw new TemplateModelException("Error reading property [" + property + "] from principal of type ["
                    + principal.getClass().getName() + "]", ex);
        }
    }
}
