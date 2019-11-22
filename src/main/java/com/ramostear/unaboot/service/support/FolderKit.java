package com.ramostear.unaboot.service.support;

import com.ramostear.unaboot.common.UnaConst;
import com.ramostear.unaboot.common.exception.NotFoundException;
import com.ramostear.unaboot.common.exception.UnaException;
import com.ramostear.unaboot.domain.vo.FolderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ ClassName FolderKit
 * @ Description 文件夹工具
 * @ Author ramostear
 * @ Date 2019/11/12 0012 5:32
 * @ Version 1.0
 **/
@Slf4j
public class FolderKit {

    /**
     * 根据目标文件夹名获取所有文件及子文件
     * @param target            相对路径
     * @return
     * @throws UnaException
     */
    public static List<FolderVO> files(String target) throws UnaException{
        Assert.hasLength(target,"磁盘路径不能为空");
        File file = new File(
                UnaConst.FILE_UPLOAD_ROOT_DIR+
                "themes"+
                UnaConst.FILE_SEPARATOR+
                target);
        if(!file.exists()){
            throw new NotFoundException("文件路径不存在");
        }
        File[] files = file.listFiles();
        List<FolderVO> folders = new ArrayList<>(files.length);
        for(File _f:files){
            FolderVO folder = new FolderVO();
            folder.setName(_f.getName());
            if(_f.isFile()){
                folder.setFolder(false);
                folder.setSize(_f.length()/1000);
            }else{
                folder.setFolder(true);
                folder.setSize(0L);
            }
            String path = _f.getAbsolutePath();
            path = path.substring(path.indexOf("themes")+7);
            folder.setDir(path.replaceAll("\\\\","/"));
            folders.add(folder);
        }
        return folders;
    }

    /**
     * 读取指定文件内容
     * @param target        文件相对路径
     * @return              文件内容
     */
    public static String reader(String target){
        String path = UnaConst.FILE_UPLOAD_ROOT_DIR+"themes"+UnaConst.FILE_SEPARATOR+target;
        InputStreamReader reader = null;
        BufferedReader buf_reader = null;
        StringBuffer buf = new StringBuffer();
        File file = new File(path);
        try{
            reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            buf_reader = new BufferedReader(reader);
            String text;
            while ((text = buf_reader.readLine()) != null){
                buf.append(text+"\n");
            }
        }catch (UnsupportedEncodingException ex){
            log.error("read file error:{}",ex.getMessage());
        }catch (FileNotFoundException ex){
            log.error("file not found.");
        }catch (IOException ex){
            log.error(ex.getMessage());
        }finally {
            try {
                reader.close();
                buf_reader.close();
            }catch (IOException ex){
                log.error(ex.getMessage());
            }
        }
        return buf.toString();
    }

    /**
     * 将新的文本内容写入文件
     * @param target            文件的相对路径
     * @param text
     * @throws IOException
     */
    public static void writer(String target,String text) throws IOException{
        String path = UnaConst.FILE_UPLOAD_ROOT_DIR+"themes"+UnaConst.FILE_SEPARATOR+target;
        OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(new File(path)),
                "UTF-8"
        );
        BufferedWriter buf_writer = new BufferedWriter(writer);
        buf_writer.write(text);
        buf_writer.close();
        writer.close();
    }

    /**
     * 新建文件
     * @param path          文件相对路径
     * @throws IOException
     */
    public static void file(String path) throws IOException{
        String target = UnaConst.FILE_UPLOAD_ROOT_DIR+"themes"+UnaConst.FILE_SEPARATOR+path;
        File file = new File(target);
        if(!file.exists()){
            file.createNewFile();
            log.info("文件已创建完成：{}",path);
        }
    }

    /**
     * 创建文件夹
     * @param path      文件夹路径
     */
    public static void folder(String path){
        String target = UnaConst.FILE_UPLOAD_ROOT_DIR+"themes"+UnaConst.FILE_SEPARATOR+path;
        File folder = new File(target);
        if(!folder.exists()){
            folder.mkdir();
            log.info("文件夹已创建完成：{}",path);
        }
    }

    /**
     * 移除指定文件，如果时文件夹，则先移除子文件
     * @param target    文件路径
     * @return
     */
    public static boolean remove(String target){
        String path = UnaConst.FILE_UPLOAD_ROOT_DIR+"themes"+UnaConst.FILE_SEPARATOR+target;
        File file = new File(path);
        return remove(file);
    }

    public static boolean remove(File file){
        if(!file.exists()){
            return false;
        }
        if(file.isDirectory()){
          File[] files = file.listFiles();
          if(files != null && files.length > 0){
              for(File f : files){
                  remove(f);
              }
          }
        }
        return file.delete();
    }

    /**
     * 拷贝文件
     * @param name
     * @throws IOException
     */
    public static void copy(String name) throws IOException{
        String source = UnaConst.FILE_UPLOAD_ROOT_DIR+"themes"+UnaConst.FILE_SEPARATOR+name;
        File file = new File(source);
        String target = new ClassPathResource("templates").getURL().getPath()
                +UnaConst.FILE_SEPARATOR
                +"themes"
                +UnaConst.FILE_SEPARATOR
                +name;
        String[] htmlFiles = file.list((dir,fileName)->{
           if(fileName.endsWith(".html")){
               return true;
           }else{
               return false;
           }
        });
        File target_file = new File(target);
        if(!target_file.exists()){
            target_file.mkdirs();
        }
        FileChannel input = null,output=null;
        for(String fileName:htmlFiles){
            input = new FileInputStream(new File(source+UnaConst.FILE_SEPARATOR+fileName)).getChannel();
            output = new FileOutputStream(new File(target+UnaConst.FILE_SEPARATOR+fileName)).getChannel();
            output.transferFrom(input,0,input.size());
        }
    }

    /**
     * 获取.html文件
     * @param target
     * @return
     */
    public static List<String> htmlFiles(String target){
        String file_path;
        try {
            file_path = ResourceUtils.getURL("classpath").getPath()
                    +UnaConst.FILE_SEPARATOR
                    +"templates"
                    +UnaConst.FILE_SEPARATOR
                    +"themes"
                    +UnaConst.FILE_SEPARATOR
                    +target;
        }catch (FileNotFoundException ex){
            log.error("file not found.");
            return Collections.emptyList();
        }
        File file = new File(file_path);
        String[] files = file.list();
        return Arrays.stream(files).collect(Collectors.toList());
    }

}
