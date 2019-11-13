package com.ramostear.unaboot.domain.dto;

import com.ramostear.unaboot.domain.dto.support.OutputConvert;
import com.ramostear.unaboot.domain.entity.Category;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName CategoryDTO
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/13 0013 0:56
 * @Version 1.0
 **/
@Data
public class CategoryDTO implements OutputConvert<CategoryDTO, Category> {

    private Integer id;

    private String name;

    private String slug;

    private Integer parentId;

    private String parentName;

    private Integer sortNum;

    private String keywords;

    private String description;

    private String template;

    private Integer allowNav;

    private Date createTime;

}
