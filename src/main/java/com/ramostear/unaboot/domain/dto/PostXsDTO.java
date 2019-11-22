package com.ramostear.unaboot.domain.dto;

import com.ramostear.unaboot.domain.dto.support.OutputConvert;
import com.ramostear.unaboot.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName PostXsDTO
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/23 0023 6:12
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostXsDTO implements OutputConvert<PostXsDTO, Post> {

    private Integer id;

    private String title;

    private String slug;

}
