package com.ramostear.unaboot.domain.vo;

import lombok.*;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPostCountVO {

    private Long postCount;

    private Integer categoryId;

    private String name;

    private String slug;

}
