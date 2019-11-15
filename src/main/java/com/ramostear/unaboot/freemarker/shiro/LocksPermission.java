package com.ramostear.unaboot.freemarker.shiro;

import com.ramostear.unaboot.freemarker.shiro.abs.PermissionModel;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
public class LocksPermission extends PermissionModel {
    @Override
    protected boolean showBody(String permit) {
        return !isPermitted(permit);
    }
}
