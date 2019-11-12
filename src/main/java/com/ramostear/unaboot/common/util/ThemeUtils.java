package com.ramostear.unaboot.common.util;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.ramostear.unaboot.common.UnaConst;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;

/**
 * @ ClassName ThemeUtils
 * @ Description 用于解压缩主题压缩包
 * @ Author ramostear
 * @ Date 2019/11/12 0012 18:35
 * @ Version 1.0
 **/
public class ThemeUtils {
    /**
     * 解压缩zip文件
     * @param source    来源路径
     * @param target    目标路径
     * @throws IOException
     */
    public static void unZip(String source,String target) throws IOException{
        unZip(new File(source),target);
    }

    /**
     * 解压缩zip文件
     * @param source        来源文件
     * @param target        目标路径
     * @throws IOException
     */
    public static void unZip(File source,String target) throws IOException{
        InputStream input = null;
        OutputStream output = null;
        try{
            File targetFile = new File(target);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            ZipFile zipFile = new ZipFile(source);
            for (Enumeration entries = zipFile.getEntries(); entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String entryName = entry.getName();
                input = zipFile.getInputStream(entry);
                String outPath = (target+"/"+entryName).replaceAll("\\*","/");
                File file = new File(outPath.substring(0,outPath.lastIndexOf("/")));
                if(!file.exists()){
                    file.mkdirs();
                }
                if(new File(outPath).isDirectory()){
                    continue;
                }
                output = new FileOutputStream(outPath);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = input.read(bytes))>0){
                    output.write(bytes,0,length);
                }
            }
            zipFile.close();
        }catch (IOException ex){
            throw new IOException(ex.getMessage());
        }finally {
            if(input != null){
                input.close();
            }
            if(output != null){
                output.close();
            }
            System.gc();
        }
    }

    /**
     * 解压缩rar文件
     * @param source    来源路径
     * @param target    目标路径
     * @throws Exception
     */
    public static void unRar(String source,String target) throws Exception{
        if(!source.toLowerCase().endsWith(".rar")){
            throw new Exception("不是指定的文件格式");
        }

        File targetFile = new File(target);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        Archive archive = null;
        try {
            archive = new Archive(new File(source));
            if(archive != null){
                FileHeader header = archive.nextFileHeader();
                while(header != null){
                    if(header.isDirectory()){
                        File file = new File(target+ UnaConst.FILE_SEPARATOR+header.getFileNameString());
                        file.mkdirs();
                    }else{
                        File file = new File(target+UnaConst.FILE_SEPARATOR+header.getFileNameString());
                        try{
                            if(!file.exists()){
                                if(!file.getParentFile().exists()){
                                    file.getParentFile().mkdirs();
                                }
                                file.createNewFile();
                            }
                            FileOutputStream outputStream = new FileOutputStream(file);
                            archive.extractFile(header,outputStream);
                            outputStream.close();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                    header = archive.nextFileHeader();
                }
            }
            archive.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
