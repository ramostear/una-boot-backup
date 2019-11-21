package com.ramostear.unaboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName FrontController
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/22 0022 3:28
 * @Version 1.0
 **/
@Controller
public class FrontController {

    @GetMapping("/index.html")
    public String index(){
        return "index";
    }
}
