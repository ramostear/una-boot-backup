package com.ramostear.unaboot.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CategoryPostCountDTO extends CategoryDTO {

    private Long postCount;

}
