package com.ramostear.unaboot.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ramostear on 2019/11/26 0026.
 */
public class UnaErrorController implements ErrorController {



    @RequestMapping(value="/error")
    public String handleError(){
        return "/themes/ramostear/404";
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}
