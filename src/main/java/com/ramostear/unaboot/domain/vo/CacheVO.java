package com.ramostear.unaboot.domain.vo;

import lombok.Data;

/**
 * @ClassName CacheVO
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/25 0025 1:34
 * @Version 1.0
 **/
@Data
public class CacheVO {

    private String name;

    private int size;

    private long memorySize;

    private int diskSize;

}
