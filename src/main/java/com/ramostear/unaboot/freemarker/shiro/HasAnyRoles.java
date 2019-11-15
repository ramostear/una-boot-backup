package com.ramostear.unaboot.freemarker.shiro;

import com.ramostear.unaboot.freemarker.shiro.abs.RoleModel;
import org.apache.shiro.subject.Subject;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
public class HasAnyRoles extends RoleModel {
    private static final String SEPARATOR = ",";
    @Override
    protected boolean showBody(String roleName) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();
        if(subject != null){
            for(String role: roleName.split(SEPARATOR)){
                if(subject.hasRole(role.trim())){
                    hasAnyRole = true;
                    break;
                }
            }
        }
        return hasAnyRole;
    }
}
