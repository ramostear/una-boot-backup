package com.ramostear.unaboot.common.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.ramostear.unaboot.common.factory.support.Qiniu;
import com.ramostear.unaboot.common.factory.support.QiniuFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Properties;
import java.util.UUID;

/**
 * @ClassName CDNUtils
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/13 0013 3:32
 * @Version 1.0
 **/
@Slf4j
public class CDNUtils {

    private CDNUtils(){}

    /**
     * 获取七牛云配置信息
     * @return
     */
    public static Qiniu getQiniu(){
        return new QiniuFactory().build();
    }

    /**
     * 设置七牛云配置信息
     * @param cdn
     */
    public static boolean setQiniu(Qiniu cdn){
        String filePath = CDNUtils.class.getClassLoader().getResource("cdn.properties").getPath();
        Properties prop = new Properties();
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            prop.load(in);
            out = new FileOutputStream(filePath);
            prop.put("qiniu.enable",cdn.isEnable()==true?"true":"false");
            prop.put("qiniu.accessKey",cdn.getAccessKey());
            prop.put("qiniu.secretKey",cdn.getSecretKey());
            prop.put("qiniu.bucket",cdn.getBucket());
            prop.put("qiniu.domain",cdn.getDomain());
            prop.store(out,"");
        } catch (FileNotFoundException e) {
            log.error("cdn.properties can not be found.");
            return false;
        }catch (IOException e) {
            log.error("load cnd.properties error.");
            return false;
        }finally {
            try {
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * 上传文件到七牛云
     * @param file
     * @return
     */
    public static String uploadToQiniu(MultipartFile file){
        Qiniu cdn = getQiniu();
        if(cdn != null && cdn.isEnable()){
            String fileName = "",suffix="",url="";
            if(file != null && !file.isEmpty()){
                suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                fileName = format.format(DateUtils.now())
                        +"-"
                        + UUID.randomUUID().toString().replaceAll("-","").toLowerCase()
                        +suffix;
                Configuration configuration = new Configuration(Zone.huadong());
                UploadManager uploadManager = new UploadManager(configuration);
                Auth auth = Auth.create(cdn.getAccessKey(),cdn.getSecretKey());
                String token = auth.uploadToken(cdn.getBucket());
                Response response;
                try {
                    response = uploadManager.put(file.getBytes(),fileName,token);
                    if(response.isOK()){
                        log.info("qiniu cdn upload info:{}",response.getInfo());
                        url = cdn.getDomain()+fileName;
                    }
                }catch (QiniuException ex){
                    log.error("upload file to qiniu cdn error:{}",ex.getMessage());
                    url = "";

                }catch (IOException ex){
                    log.error("upload file error:{}",ex.getMessage());
                    url = "";
                }
                return url;
            }
        }
        return "";
    }

    /**
     * 从七牛云删除文件
     * @param url
     * @return
     */
    public static boolean deleteFromQiniu(String url){
        Qiniu cdn = getQiniu();
        if(cdn != null){
            Auth auth = Auth.create(cdn.getAccessKey(),cdn.getSecretKey());
            Configuration configuration = new Configuration(Zone.huadong());
            BucketManager bucketManager = new BucketManager(auth,configuration);
            String key = url.replace(cdn.getDomain(),"");
            try {
                bucketManager.delete(cdn.getBucket(),key);
            }catch (QiniuException ex){
                log.error("delete file from qiniu cdn error:{}",ex.getMessage());
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * 从七牛云存储删除多个文件
     * @param urls
     * @return
     */
    public static boolean deleteFromQiniu(Collection<String> urls){
        Qiniu cdn = getQiniu();
        if(cdn != null){
            Auth auth = Auth.create(cdn.getAccessKey(),cdn.getSecretKey());
            Configuration configuration = new Configuration(Zone.huadong());
            BucketManager bucketManager = new BucketManager(auth,configuration);
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            urls.forEach(url->{
                String key = url.replace(cdn.getDomain(),"");
                batchOperations.addDeleteOp(cdn.getBucket(),key);
            });
            try {
                bucketManager.batch(batchOperations);
                return true;
            }catch (QiniuException ex){
                log.error("delete from qiniu error:{}",ex.getMessage());
                return false;
            }
        }else{
            return false;
        }
    }
}
