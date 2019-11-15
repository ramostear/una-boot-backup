package com.ramostear.unaboot.domain.param;

import com.ramostear.unaboot.common.util.DateUtils;
import com.ramostear.unaboot.domain.dto.support.InputConvert;
import com.ramostear.unaboot.domain.entity.Link;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Ramostear on 2019/11/15 0015.
 */
@Data
public class LinkParam implements InputConvert<Link> {

    @NotBlank(message = "友情连接名称不能为空")
    @Size(max = 12,message = "连接名称不能超过{max}个字符长度")
    private String name;

    @NotBlank(message = "友情链接地址不能为空")
    @Size(max = 128,message = "连接地址长度不能超过{max}个字符长度")
    private String url;

    @Min(value = 0,message = "排序号最小不能小于0")
    private Integer sortNum;

    private Date createTime;

    @Override
    public Link convertTo() {
        createTime = DateUtils.now();
        Link link = InputConvert.super.convertTo();
        return link;
    }
}
