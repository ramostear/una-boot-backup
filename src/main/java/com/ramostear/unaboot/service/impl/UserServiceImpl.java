package com.ramostear.unaboot.service.impl;

import com.ramostear.unaboot.domain.entity.User;
import com.ramostear.unaboot.repository.UserRepository;
import com.ramostear.unaboot.service.UserService;
import com.ramostear.unaboot.service.support.UnaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @ ClassName UserServiceImpl
 * @ Description 用户业务接口实现类
 * @ Author ramostear
 * @ Date 2019/11/9 0009 0:43
 * @ Version 1.0
 **/
@Service("userService")
public class UserServiceImpl extends UnaService<User,Integer> implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        Assert.notNull(username,"username must not be null.");
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByPrincipal(String principal) {
        if(StringUtils.isBlank(principal)){
            return Optional.empty();
        }
        User user = userRepository.findByUsername(principal);
        if(user != null){
            return Optional.of(user);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public User updatePassword(Integer userId, String oldPassword, String newPassword) {
        Assert.notNull(userId,"user id must not be null.");
        Assert.notNull(oldPassword,"user old password must not be null.");
        Assert.notNull(newPassword,"user new password must not be null.");
        Assert.isTrue(!oldPassword.equalsIgnoreCase(newPassword),"the new password can not be the same as the old password.");

        Optional<User> optional = userRepository.findById(userId);
        Assert.isTrue(optional.isPresent(),"user does not exists.");

        User user = optional.get();
        String _password = setPassword(user,oldPassword);
        Assert.isTrue((user.getPassword().equals(_password)),"user password error.");
        setPassword(user,newPassword);
        return update(user);
    }

    @Override
    public User create(User user) {
        Assert.notNull(user,"User must not be null.");
        Assert.notNull(user.getUsername(),"User username must not be null.");
        Assert.notNull(user.getUsername(),"User password must not be null.");

        user.setPassword(new SimpleHash("MD5",user.getPassword(), ByteSource.Util.bytes(user.getUsername()),1024).toString());
        return super.create(user);
    }


    private String setPassword(User user,String password){
        user.setPassword(new SimpleHash("MD5",
                password,
                ByteSource.Util.bytes(user.getUsername()),
                1024).toString());
        return user.getPassword();
    }

}
