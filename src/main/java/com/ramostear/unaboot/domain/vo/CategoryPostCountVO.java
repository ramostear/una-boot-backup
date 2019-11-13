package com.ramostear.unaboot.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
@ToString
@EqualsAndHashCode
public class CategoryPostCountVO {

    private Long postCount;

    private Integer categoryId;

    private String name;

    private String slug;

}
