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
 * @ClassName QiniuUtils
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/8 0008 23:42
 * @Version 1.0
 **/
@Slf4j
public class QiniuUtils {

    /**
     * 获取七牛云配置信息
     * @return Qiniu
     */
    public static Qiniu getConfig(){
        return new QiniuFactory().build();
    }

    /**
     * 设置配置文件
     * @param qiniu
     * @return  true or false
     */
    public static boolean setConfig(Qiniu qiniu){
        String conf = QiniuUtils.class.getClassLoader().getResource("cdn.properties").getPath();
        Properties prop = new Properties();
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(conf));
            prop.load(in);
            out = new FileOutputStream(conf);
            prop.put("qiniu.enable",qiniu.isEnable()==true?"true":"false");
            prop.put("qiniu.accessKey",qiniu.getAccessKey());
            prop.put("qiniu.secretKey",qiniu.getSecretKey());
            prop.put("qiniu.bucket",qiniu.getBucket());
            prop.put("qiniu.domain",qiniu.getDomain());
            prop.store(out,"");
        }catch (FileNotFoundException ex){
           log.error("can not fount cdn.properties file");
           return false;
        }catch (IOException ex){
            log.error("load cdn.properties file error");
            return false;
        }finally {
            try {
                in.close();
                out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String upload(MultipartFile multipartFile){
        Qiniu qiniu = getConfig();
        if(qiniu != null && qiniu.isEnable()){
            String fileName = "",suffix="",url="";
            if(multipartFile != null && !multipartFile.isEmpty()){
                suffix = multipartFile.getOriginalFilename()
                        .substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                fileName = format.format(DateUtils.now())
                        +"-"
                        + UUID.randomUUID().toString().replaceAll("-","").toLowerCase()
                        +suffix;
                Configuration configuration = new Configuration(Zone.huadong());
                UploadManager uploadManager = new UploadManager(configuration);
                Auth auth = Auth.create(qiniu.getAccessKey(),qiniu.getSecretKey());
                String token = auth.uploadToken(qiniu.getBucket());
                Response response;
                try {
                    response = uploadManager.put(multipartFile.getBytes(),fileName,token);
                    if(response.isOK()){
                        log.info("qiniu cdn upload info:{}",response.getInfo());
                        url = qiniu.getDomain()+fileName;
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
     * 根据URL移除文件
     * @param url
     * @return
     */
    public static boolean remove(String url){
        Qiniu qiniu = getConfig();
        if(qiniu != null){
            Auth auth = Auth.create(qiniu.getAccessKey(),qiniu.getSecretKey());
            Configuration configuration = new Configuration(Zone.huadong());
            BucketManager bucketManager = new BucketManager(auth,configuration);
            String key = url.replace(qiniu.getDomain(),"");
            try {
                bucketManager.delete(qiniu.getBucket(),key);
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
     * 批量移除多个文件
     * @param urls
     * @return
     */
    public static boolean remove(Collection<String> urls){
        Qiniu qiniu = getConfig();
        if(qiniu != null){
            Auth auth = Auth.create(qiniu.getAccessKey(),qiniu.getSecretKey());
            Configuration configuration = new Configuration(Zone.huadong());
            BucketManager bucketManager = new BucketManager(auth,configuration);
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            urls.forEach(url->{
                String key = url.replace(qiniu.getDomain(),"");
                batchOperations.addDeleteOp(qiniu.getBucket(),key);
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
