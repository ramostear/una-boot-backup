package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.domain.entity.User;
import com.ramostear.unaboot.repository.UserRepository;
import com.ramostear.unaboot.service.UserService;
import com.ramostear.unaboot.service.support.UnaService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/9 0009 0:43
 * @Version 1.0
 **/
@Service("userService")
public class UserServiceImpl extends UnaService<User,Integer> implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }
}
