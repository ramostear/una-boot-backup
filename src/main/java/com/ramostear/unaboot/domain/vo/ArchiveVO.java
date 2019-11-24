package com.ramostear.unaboot.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ArchiveVO
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/16 0016 20:39
 * @Version 1.0
 **/
@Data
public class ArchiveVO implements Serializable {
    private static final long serialVersionUID = 8604800275515239966L;
    private String name;
    private Long count;
}
