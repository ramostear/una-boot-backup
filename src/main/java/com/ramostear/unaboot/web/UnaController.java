package com.ramostear.unaboot.web;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName UnaController
 * @Description 公共的控制器
 * @Author ramostear
 * @Date 2019/11/12 0012 3:00
 * @Version 1.0
 **/
public class UnaController {

    @InitBinder
    public void initBinder(ServletRequestDataBinder dataBinder){
        dataBinder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
    }

    /**
     * 默认分页
     * @return  Pageable
     */
    protected Pageable page(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        int size = ServletRequestUtils.getIntParameter(request,"size",15);
        int offset = ServletRequestUtils.getIntParameter(request,"offset",1);
        return PageRequest.of(offset-1,size);
    }

    /**
     * 根据排序字段降序排列分页数据
     * @param field     排序字段
     * @return          Pageable
     */
    protected Pageable pageByDesc(String field){
        return initPage(field,0);
    }
    protected Pageable pageByDesc(String field,int size){
        return initPage(field,0,size);
    }

    /**
     * 根据排序字段升序排列分页数据
     * @param field     排序字段
     * @return          Pageable
     */
    protected Pageable pageByAsc(String field){
        return initPage(field,1);
    }
    protected Pageable pageByAsc(String field,int size){
        return initPage(field,1,size);
    }

    /**
     * 路径跳转命令
     * @param path  跳转路径
     * @return      String
     */
    protected String redirect(String path){
        return "redirect:"+path;
    }


    private Pageable initPage(String field,int type){
        return initPage(field,type,15);
    }
    private Pageable initPage(String field,int type,int _size){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        int size = ServletRequestUtils.getIntParameter(request,"size",_size);
        int offset = ServletRequestUtils.getIntParameter(request,"offset",1);
        if(type == 0){
            return PageRequest.of(offset-1,size,new Sort(Sort.Direction.DESC,field));
        }else{
            return PageRequest.of(offset-1,size,new Sort(Sort.Direction.ASC,field));
        }
    }
}
