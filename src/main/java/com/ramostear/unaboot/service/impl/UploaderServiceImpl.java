package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.common.factory.support.Qiniu;
import com.ramostear.unaboot.common.util.CDNUtils;
import com.ramostear.unaboot.common.util.DateUtils;
import com.ramostear.unaboot.service.UploaderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.UUID;

/**
 * @ClassName UploaderServiceImpl
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/13 0013 3:45
 * @Version 1.0
 **/
@Slf4j
@Service("uploaderService")
public class UploaderServiceImpl implements UploaderService {
    @Override
    public String upload(MultipartFile file) {
        Qiniu qiniu = CDNUtils.getQiniu();
        if(qiniu != null && qiniu.isEnable()){
            return CDNUtils.uploadToQiniu(file);
        }
        return uploadToLocal(file);
    }

    @Override
    public void delete(String url) {
        deleteFile(url);
    }

    @Override
    public void delete(Collection<String> urls) {
        if(!CollectionUtils.isEmpty(urls)){
            urls.forEach(url->{
                deleteFile(url);
            });
        }
    }

    private String uploadToLocal(MultipartFile file){
        String upload_root_path = UnaConst.FILE_UPLOAD_ROOT_DIR+"store";
        String path;
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String fileName = format.format(DateUtils.now())
                +"-"
                + UUID.randomUUID().toString().replaceAll("-","").toLowerCase()
                +suffix;
        path = UnaConst.FILE_SEPARATOR+"store"+UnaConst.FILE_SEPARATOR+fileName;
        File temp = new File(upload_root_path+UnaConst.FILE_SEPARATOR+fileName);
        if(!temp.getParentFile().exists()){
            temp.getParentFile().mkdirs();
        }
        try {
            file.transferTo(temp);
        }catch (IOException ex){
            log.error("upload file to local error:{}",ex.getMessage());
            return "";
        }
        return path;
    }

    private void deleteFile(String url){
        if(StringUtils.isNotBlank(url)){
            if(url.startsWith("http://")||url.startsWith("https://")){
                CDNUtils.deleteFromQiniu(url);
            }else{
                String path = UnaConst.FILE_UPLOAD_ROOT_DIR+url;
                File file = new File(path);
                if(file.exists() && file.isFile()){
                    file.delete();
                }
            }
        }
    }
}
