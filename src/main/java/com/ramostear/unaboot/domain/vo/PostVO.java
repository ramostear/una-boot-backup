package com.ramostear.unaboot.domain.vo;

import com.ramostear.unaboot.domain.dto.PostDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostVO extends PostDTO {

    private Set<Integer> tagIds;

    private Integer categoryId;
}
