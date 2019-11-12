package com.ramostear.unaboot.web.admin;

import com.alibaba.fastjson.JSONObject;
import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.service.UploaderService;
import com.ramostear.unaboot.web.UnaController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ ClassName UploaderController
 * @ Description 文件上传控制器
 * @ Author ramostear
 * @ Date 2019/11/13 0013 3:42
 * @ Version 1.0
 **/
@RestController
@RequestMapping("/admin/uploader")
@RequiresRoles(value = {UnaConst.DEFAULT_ROLE_NAME})
public class UploaderController extends UnaController {

    private final UploaderService uploaderService;

    public UploaderController(UploaderService uploaderService){
        this.uploaderService = uploaderService;
    }

    /**
     * markdown图片文件上传
     * @param file      图片文件
     * @return          上传信息
     */
    @PostMapping("/editormd")
    public JSONObject uploadEditorMdImg(@RequestParam(name = "editormd-image-file") MultipartFile file){
        JSONObject json = new JSONObject();
        if(file == null || file.isEmpty()){
            return convertTo(json,0,"none","");
        }
        if(StringUtils.isBlank(file.getOriginalFilename()) && !allow(file.getOriginalFilename())){
            return convertTo(json,0,"error","");
        }
        String url = uploaderService.upload(file);
        if(StringUtils.isBlank(url)){
            return convertTo(json,0,"failure","");
        }else{
            return convertTo(json,1,"success",url);
        }

    }

    private JSONObject convertTo(JSONObject json,int status,String msg,String url){
        json.put("success",status);
        json.put("message",msg);
        json.put("url",url);
        return json;
    }

    private boolean allow(String fileName){
        String[] allowFiles = {".gif",".png",".jpg",".jpeg",".bpm"};
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        List<String> suffixList = Arrays.stream(allowFiles).collect(Collectors.toList());
        return suffixList.contains(suffix);
    }
}
