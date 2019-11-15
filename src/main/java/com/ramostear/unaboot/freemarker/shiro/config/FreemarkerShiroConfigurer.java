package com.ramostear.unaboot.freemarker.shiro.config;

import com.ramostear.unaboot.freemarker.shiro.*;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Service
@Slf4j
public class FreemarkerShiroConfigurer extends SimpleHash {

    private Configuration configuration;

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException{
        configuration.setSharedVariable("shiro",new FreemarkerShiroConfigurer());
        log.debug("init freemarker-shiro tag name as shiro");
    }

    public FreemarkerShiroConfigurer(){
        put("authenticated",new Authenticated());
        put("guest",new Guest());
        put("hasAnyRoles",new HasAnyRoles());
        put("hasPermission",new HasPermission());
        put("hasRole",new HasRole());
        put("locksPermission",new LocksPermission());
        put("locksRole",new LocksRole());
        put("notAuthenticated",new NotAuthenticated());
        put("user",new User());
    }

}
