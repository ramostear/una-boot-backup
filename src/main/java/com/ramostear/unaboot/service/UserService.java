package com.ramostear.unaboot.service;

import com.ramostear.unaboot.domain.entity.User;
import com.ramostear.unaboot.service.support.IUnaService;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserService  extends IUnaService<User,Integer> {

    /**
     * 根据用户名查询用户信息
     * @param username      用户名
     * @return              User
     */
    User findByUsername(String username);

    /**
     * 根据登录身份查询用户信息
     * @param principal     登录身份
     * @return              Optional
     */
    @NonNull
    Optional<User> findByPrincipal(@NonNull String principal);


    /**
     * 更新用户密码
     * @param userId            用户ID
     * @param oldPassword       旧密码
     * @param newPassword       新密码
     * @return
     */
    @NonNull
    User updatePassword(@NonNull Integer userId,@NonNull String oldPassword,@NonNull String newPassword);

}
