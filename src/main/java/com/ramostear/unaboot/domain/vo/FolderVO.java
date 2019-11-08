package com.ramostear.unaboot.domain.vo;

import lombok.Data;

/**
 * @ClassName FolderVO
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:28
 * @Version 1.0
 **/
@Data
public class FolderVO {
    private String name;

    private String dir;

    private long size;

    private boolean folder;


    public Long getSize(){
        if(folder){
            size = 0;
        }
        return size;
    }
    public void setSize(Long size){
        if(folder){
            this.size = 0;
        }else{
            this.size = size;
        }
    }
}
