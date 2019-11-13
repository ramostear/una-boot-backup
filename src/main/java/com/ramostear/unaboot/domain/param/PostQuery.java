package com.ramostear.unaboot.domain.param;

import lombok.Data;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
public class PostQuery {

    private String keyword;

    private Integer status;

    private Integer categoryId;

}
