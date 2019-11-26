package com.ramostear.unaboot.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ramostear on 2019/11/26 0026.
 */
@Slf4j
@Component
public class ErrorInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(response.getStatus() == 500 || response.getStatus() == 404){
            log.info("response status:[{}]",response.getStatus());
            if(modelAndView == null){
                modelAndView = new ModelAndView();
            }
            modelAndView.setViewName("/themes/ramostear/404");
        }
    }
}
