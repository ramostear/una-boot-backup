package com.ramostear.unaboot.domain.vo;

import com.ramostear.unaboot.domain.dto.PostSimpleDTO;
import com.ramostear.unaboot.domain.entity.Category;
import com.ramostear.unaboot.domain.entity.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ramostear on 2019/11/13 0013.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostListVO extends PostSimpleDTO implements Serializable {

    private static final long serialVersionUID = 1394952277427768642L;
    private String author;

    private Long chatCount = 0L;

    private List<Tag> tags;

    private Category category;

}
