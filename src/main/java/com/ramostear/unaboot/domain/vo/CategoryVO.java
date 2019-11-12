package com.ramostear.unaboot.domain.vo;

import com.ramostear.unaboot.domain.dto.CategoryDTO;
import lombok.Data;

import java.util.List;

/**
 * @ ClassName CategoryVO
 * @ Description TODO
 * @ Author ramostear
 * @ Date 2019/11/13 0013 1:01
 * @ Version 1.0
 **/
@Data
public class CategoryVO extends CategoryDTO {

    private List<CategoryVO> children;
}
