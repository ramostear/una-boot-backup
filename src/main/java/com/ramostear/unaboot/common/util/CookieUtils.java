package com.ramostear.unaboot.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @ClassName CookieUtils
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/23 0023 4:10
 * @Version 1.0
 **/
@Slf4j
public class CookieUtils {

    private CookieUtils(){}

    /**
     * 获取所有的cookie
     * @return
     */
    public static Cookie[] cookies(){
        ServletRequestAttributes attributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie[] cookies = request.getCookies();
        return cookies;
    }

    public static void write(String key,String value,int maxAge){
        try{
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            value = URLEncoder.encode(value,"utf-8");
            Cookie cookie = new Cookie(key,value);
            cookie.setDomain(attributes.getRequest().getContextPath());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(maxAge);
            write(cookie);
        }catch (UnsupportedEncodingException ex){
            log.warn(ex.getMessage());
        }
    }

    public static void write(Cookie cookie){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.addCookie(cookie);
    }

    public static String read(String key){
        try{
            Cookie[] cookies = cookies();
            for(int i=0;i<(cookies == null?0:cookies.length);i++){
                if(key.equalsIgnoreCase(cookies[i].getValue())){
                    return URLDecoder.decode(cookies[i].getValue(),"utf-8");
                }
            }
            return null;
        }catch (UnsupportedEncodingException ex){
            log.warn(ex.getMessage());
            return null;
        }
    }
}
