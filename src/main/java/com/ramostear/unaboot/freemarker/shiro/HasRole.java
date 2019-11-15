package com.ramostear.unaboot.freemarker.shiro;

import com.ramostear.unaboot.freemarker.shiro.abs.RoleModel;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
public class HasRole extends RoleModel {
    @Override
    protected boolean showBody(String roleName) {
        return getSubject() != null && getSubject().hasRole(roleName);
    }
}
