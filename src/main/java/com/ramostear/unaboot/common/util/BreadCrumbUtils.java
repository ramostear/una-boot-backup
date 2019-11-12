package com.ramostear.unaboot.common.util;

import com.ramostear.unaboot.domain.vo.BreadcrumbVO;

/**
 * @ ClassName BreadCrumbUtils
 * @ Description 面包屑工具类
 * @ Author ramostear
 * @ Date 2019/11/12 0012 18:49
 * @ Version 1.0
 **/
public class BreadCrumbUtils {
    /**
     * 将磁盘路径转换为面包屑
     * @param path      磁盘路径
     * @return          面包屑数组
     */
    public static BreadcrumbVO[] breadcrumb(String path){
        if(path == null || path.trim().equals("")){
            return new BreadcrumbVO[]{};
        }
        String[] pathArray = path.split("/");
        BreadcrumbVO[] breadcrumbs = new BreadcrumbVO[pathArray.length];
        for(int i=0;i<pathArray.length;i++){
            if(i==0){
                breadcrumbs[i] = BreadcrumbVO.builder().name(pathArray[i]).link(pathArray[i]).build();
            }else{
                String link = "";
                for(int j=0;j<=i;j++){
                    link+=pathArray[j];
                    if(j != i){
                        link+="/";
                    }
                }
                breadcrumbs[i] = BreadcrumbVO.builder().name(pathArray[i]).link(link).build();
            }
        }
        return breadcrumbs;
    }
}
