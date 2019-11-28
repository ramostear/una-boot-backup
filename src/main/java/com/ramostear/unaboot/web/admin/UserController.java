package com.ramostear.unaboot.web.admin;

import com.ramostear.unaboot.common.exception.UnaException;
import com.ramostear.unaboot.service.UserService;
import com.ramostear.unaboot.web.UnaController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ramostear on 2019/11/26 0026.
 */
@Controller
@RequestMapping("/admin/user")
@RequiresUser
public class UserController extends UnaController {


    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public String profile(){
        return "/admin/user/profile";
    }

    @PostMapping("/{userId}/password")
    @ResponseBody
    public ResponseEntity<Object> updatePassword(@PathVariable("userId")Integer userId,
                                                 @RequestParam(name = "oldPassword")String oldPassword,
                                                 @RequestParam(name = "newPassword")String newPassword){
        try {
            userService.updatePassword(userId,oldPassword,newPassword);
            SecurityUtils.getSubject().logout();
            return ok();
        }catch (UnaException ex){
            return badRequest();
        }
    }
}
