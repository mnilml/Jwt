package com.lml.jwtlogin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lml.jwtlogin.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDao extends BaseMapper<User> {
//    User login(User user);
    @Insert("insert into user (username,password,create_time,login_time) values(#{username},#{password},#{createTime},#{loginTime})")
    int register(User user);


}

