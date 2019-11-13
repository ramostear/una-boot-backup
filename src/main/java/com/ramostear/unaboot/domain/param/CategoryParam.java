package com.ramostear.unaboot.domain.param;

import com.ramostear.unaboot.common.util.PinyinUtils;
import com.ramostear.unaboot.domain.dto.support.InputConvert;
import com.ramostear.unaboot.domain.entity.Category;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @ClassName CategoryParam
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/13 0013 2:23
 * @Version 1.0
 **/
@Data
public class CategoryParam implements InputConvert<Category> {

    @NotBlank(message = "category name must not be empty")
    @Size(max = 50,message = "the length of category name cannot be greater than {max}")
    private String name;

    @Size(max = 50,message = "the length of category slug name cannot be greater than {max}")
    private String slug;

    private Integer parentId = 0;

    private Integer sortNum = 0;

    @Size(max = 100,message = "the length of category keyword cannot be greater than {max}")
    private String keywords;

    @Size(max = 200,message = "the length of category description cannot be greater than {max}")
    private String description;

    @Size(max=50,message = "the length of category template name cannot be greater than {max}")
    private String template;

    private Integer allowNav = 0;

    @Override
    public Category convertTo() {
        if(StringUtils.isBlank(slug)){
            slug = PinyinUtils.convertToFirstChar(name);
        }else{
            slug = PinyinUtils.convertTo(slug);
        }
        return InputConvert.super.convertTo();
    }
}
