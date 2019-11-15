package com.ramostear.unaboot.domain.dto;

import com.ramostear.unaboot.domain.dto.support.OutputConvert;
import com.ramostear.unaboot.domain.entity.Link;
import lombok.Data;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Data
public class LinkDTO implements OutputConvert<LinkDTO, Link> {

    private Integer id;

    private String name;

    private String url;

    private Integer sortNum;

}
