package com.lml.jwtlogin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private  Integer id;
    private  String username;
    private  String password;
    private  Date createTime;
    private  Date loginTime;

    private String avata;
//    public User(String username,String password){
//        this.username=username;
//        this.password=password;
//    }

}
