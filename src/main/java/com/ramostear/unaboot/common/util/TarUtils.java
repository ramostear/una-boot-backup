package com.ramostear.unaboot.common.util;

import com.ramostear.unaboot.common.UnaConst;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @ClassName TarUtils
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/8 0008 23:59
 * @Version 1.0
 **/
public class TarUtils {

    /**
     * 解压缩文件
     * @param source    资源路径
     * @param target    目标路径
     * @throws IOException
     */
    public static void unzip(String source,String target) throws IOException {
        unzip(new File(source),target);
    }

    /**
     * 解压缩文件
     * @param source    资源文件
     * @param target    目标路径
     * @throws IOException
     */
    public static void unzip(File source,String target) throws IOException{
        InputStream in = null;
        OutputStream out = null;
        try{
            File targetFile = new File(target);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            ZipFile zip = new ZipFile(source);
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String entryName = entry.getName();
                in = zip.getInputStream(entry);
                String outPath = (target+ UnaConst.FILE_SEPARATOR+entryName).replaceAll("\\*","/");
                File file = new File(outPath.substring(0,outPath.lastIndexOf("/")));
                if(!file.exists()){
                    file.mkdirs();
                }
                if(new File(outPath).isDirectory()){
                    continue;
                }
                out = new FileOutputStream(outPath);
                byte[] bytes = new byte[1024];
                int len;
                while ((len = in.read(bytes))>0){
                    out.write(bytes,0,len);
                }
            }
            zip.close();
        }catch (IOException ex){
            throw new IOException(ex.getMessage());
        }finally {
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
        }
    }
}
