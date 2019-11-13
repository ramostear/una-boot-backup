package com.ramostear.unaboot.domain.param;

import com.ramostear.unaboot.domain.dto.support.InputConvert;
import com.ramostear.unaboot.domain.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * @ClassName UserParam
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:25
 * @Version 1.0
 **/
@Data
public class UserParam implements InputConvert<User> {

    @NotBlank(message = "username must not be null")
    @Size(max = 64,message = "The length of username can not exceed {max}")
    private String username;

    @Size(min = 8,max = 64,message = "password length must be between {min} and {max}")
    private String password;

    @NotBlank(message = "role name must not be null")
    @Size(max = 64,message = "the length of role name can not exceed {max}")
    private String role;
}
