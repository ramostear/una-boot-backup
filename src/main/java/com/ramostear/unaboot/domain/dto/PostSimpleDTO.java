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
public class PostSimpleDTO extends PostMiniDTO {

    private String summary;

    private String thumb;

    private Long visits = 0L;

    private Long likes = 0L;

    private String template;

    private Boolean allowChat;

    private Boolean allowTop;

    private Boolean allowFavor;

    private Boolean original;

    private String projectUrl;

}
