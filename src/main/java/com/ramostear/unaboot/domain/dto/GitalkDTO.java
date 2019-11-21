package com.ramostear.unaboot.domain.dto;

import lombok.Data;

/**
 * @ClassName GitalkDTO
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/22 0022 0:43
 * @Version 1.0
 **/
@Data
public class GitalkDTO {

    private Integer enable = 0;

    private String clientId;

    private String clientSecret;

    private String repo;

    private String owner;

    private String admin;

}
