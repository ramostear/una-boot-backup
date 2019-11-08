package com.ramostear.unaboot.common.factory.support;

import com.ramostear.unaboot.common.factory.CDN;
import com.ramostear.unaboot.common.factory.CDNFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName QiniuFactory
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/8 0008 23:30
 * @Version 1.0
 **/
@Slf4j
public class QiniuFactory extends CDNFactory {
    @Override
    public Qiniu build() {
        Qiniu qiniu = new Qiniu();
        Properties prop = new Properties();
        String conf = QiniuFactory.class.getClassLoader().getResource("cdn.properties").getPath();
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(conf);
            prop.load(inputStream);
            qiniu.setEnable(prop.getProperty("qiniu.enable","false").equals("true"));
            qiniu.setAccessKey(prop.getProperty("qiniu.accessKey",""));
            qiniu.setAccessKey(prop.getProperty("qiniu.secretKey",""));
            qiniu.setAccessKey(prop.getProperty("qiniu.bucket",""));
            qiniu.setAccessKey(prop.getProperty("qiniu.domain",""));
            return qiniu;
        }catch (FileNotFoundException ex){
            log.error("cdn config file can not found");
        }catch (IOException ioe){
            log.error("load cdn file error");
        }
        return null;
    }
}
