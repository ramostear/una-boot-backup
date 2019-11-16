package com.ramostear.unaboot.domain.dto;

import com.ramostear.unaboot.domain.dto.support.OutputConvert;
import com.ramostear.unaboot.domain.entity.Post;
import lombok.*;

import java.util.Date;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PostMiniDTO implements OutputConvert<PostMiniDTO, Post> {

    private Integer id;

    private String title;

    private String slug;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
