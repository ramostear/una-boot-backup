package com.ramostear.unaboot.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName TalkVO
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/22 0022 1:47
 * @Version 1.0
 **/
@Data
public class TalkVO {

    private String login;

    private String body;

    private Date createAt;

    private String avatarUrl;

    private String htmlUrl;

}
