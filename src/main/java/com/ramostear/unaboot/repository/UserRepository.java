package com.ramostear.unaboot.repository;

import com.ramostear.unaboot.domain.entity.User;
import com.ramostear.unaboot.repository.support.UnaRepository;

public interface UserRepository extends UnaRepository<User,Integer> {

    /**
     * 根据用户名查找用户信息
     * @param username      用户名
     * @return              User
     */
    User findByUsername(String username);
}
