package com.lml.jwtlogin.controller;

import com.lml.jwtlogin.Utils.JwtUtils;
import com.lml.jwtlogin.entity.User;
import com.lml.jwtlogin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/register")
    public Map<String,Object> register(User user){
//        UserDao.register(user);
        Map<String,Object> map=new HashMap<>();
        try{
            userService.register(user);
            map.put("mag","注册成功");
            map.put("code","200");
        }
        catch (Exception ex){

            map.put("msg","注册失败:"+ex.getMessage());
        }

        return map;
    }
    @GetMapping("/user/login")
    public Map<String,Object> login(User user)
    {
        log.info("用户名："+user.getUsername());
        log.info("密码："+user.getPassword());
        Map<String,Object> map=new HashMap<>();
        try {
//
            userService.login(user);
//            user.setLogintime(new Date());
            map.put("msg","登录成功");
            map.put("code","200");

            Map<String,String> payload=new HashMap<>();
            payload.put("name",user.getUsername());
            String token= JwtUtils.getToken(payload);
            map.put("token",token);
        }
        catch (Exception ex)
        {
            map.put("msg",ex.getMessage());
        }

        return map;
    }

    @PostMapping("/test/verity")
    public  Map<String,String> verityToken()
    {
        Map<String, String> map=new HashMap<>();
        map.put("msg","验证成功");
        map.put("code","200");
        return map;
    }




}
