package com.ramostear.unaboot.web.admin;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.common.exception.UnaException;
import com.ramostear.unaboot.domain.entity.User;
import com.ramostear.unaboot.web.UnaController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @ ClassName GuardController
 * @ Description 后台守卫控制器
 * @ Author ramostear
 * @ Date 2019/11/12 0012 4:00
 * @ Version 1.0
 **/
@Slf4j
@Controller
public class GuardController extends UnaController {

    private static final String MSG = "用户名或密码错误";

    /**
     * 进入后台控制面板
     * @param model
     * @return          视图模板路径：/admin/dashboard.html
     */
    @RequiresRoles(value = {UnaConst.DEFAULT_ROLE_NAME})
    @GetMapping("/admin/dashboard")
    public String dashboard(Model model){
        model.addAttribute("dashboard","");
        return "/admin/dashboard";
    }

    /**
     * 后台登录地址   ：/admin/login
     * @return      视图模板路径：/auth/login.html
     */
    @GetMapping("/admin/login")
    public String login(){
        User user = (User) SecurityUtils.getSubject()
                .getSession().getAttribute("profile");
        if(user != null){
            return redirect("/admin/dashboard");
        }else{
            return "/auth/login";
        }
    }

    /**
     * 登录控制器
     * @param username      用户名
     * @param password      密码
     * @param model
     * @return
     */
    @PostMapping("/admin/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, Model model){

        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            model.addAttribute("msg",MSG);
            return "/auth/login";
        }
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            if(subject.isAuthenticated()){
                return redirect("/admin/dashboard");
            }else{
                model.addAttribute("msg",MSG);
            }
        }catch (AccountException ex){
            log.warn("登录账户异常:{}",ex.getMessage());
            model.addAttribute("msg",MSG);
        }catch (AuthenticationException ex){
            log.warn("登录账户权限异常：{}",ex.getMessage());
            model.addAttribute("msg",MSG);
        }catch (UnaException ex){
            log.error("登录异常:{}",ex.getMessage());
            model.addAttribute("msg",MSG);
        }
        return "/auth/login";
    }

    @RequiresUser
    @GetMapping("/admin/logout")
    public String logout(HttpServletResponse response){
        SecurityUtils.getSubject().logout();
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        return redirect("/admin/login");
    }
}
