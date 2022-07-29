package com.lml.jwtlogin.service;

import com.lml.jwtlogin.entity.User;

public interface UserService {
    User login(User user);//登录接口

    int register(User user);//注册接口

    User upload(User user);
}
