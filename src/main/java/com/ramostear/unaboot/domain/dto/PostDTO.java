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
public class PostDTO extends PostSimpleDTO {

    private String html;

    private String markdown;

    private String author;

    private String keywords;

    private String description;
}
