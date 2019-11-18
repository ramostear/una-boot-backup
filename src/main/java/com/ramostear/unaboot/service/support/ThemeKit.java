package com.ramostear.unaboot.service.support;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.common.exception.UnaException;
import com.ramostear.unaboot.common.util.ThemeUtils;
import com.ramostear.unaboot.domain.entity.Theme;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @ClassName ThemeKit
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/12 0012 18:22
 * @Version 1.0
 **/
public class ThemeKit {

    public static Theme compress(HttpServletRequest request, CommonsMultipartFile file) throws Exception{
        if(file.isEmpty()){
            throw new FileNotFoundException("上传文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        int point_index = fileName.lastIndexOf(".");
        String ext_name = fileName.substring(point_index+1).toLowerCase();
        if(!ext_name.equals("zip") && !ext_name.equals("rar")){
            throw new Exception("主题文件格式不正确");
        }
        String theme_name = fileName.substring(0,point_index);
        String root = UnaConst.FILE_UPLOAD_ROOT_DIR;
        String theme_path = root+"themes"+UnaConst.FILE_SEPARATOR+fileName;
        String theme_folder = root+"themes"+UnaConst.FILE_SEPARATOR+theme_name;

        File theme = new File(theme_path);
        if(!theme.exists()){
            theme.mkdirs();
        }
        file.transferTo(theme);
        if(ext_name.equals("zip")){
            ThemeUtils.unZip(theme_path,theme_folder);
        }else if(ext_name.equals("rar")){
            ThemeUtils.unRar(theme.getAbsolutePath(),theme_folder);
        }else{
            throw new Exception("不支持该类型的文件");
        }

        Theme _theme = new Theme();
        _theme.setName(theme_name);
        _theme.setThumb("themes"+UnaConst.FILE_SEPARATOR+theme_name+UnaConst.FILE_SEPARATOR+"thumb.png");
        System.gc();
        return _theme;
    }
}
