package com.lml.jwtlogin.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lml.jwtlogin.dao.UserDao;
import com.lml.jwtlogin.entity.User;
import com.lml.jwtlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class UserServiceImpI implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        user.setPassword(this.MD5Code(user.getPassword()));

        User curUser = this.checkUserIsExit(user);
        if(curUser!=null) {

            // 更新用户最后登录时间
            curUser.setLoginTime(new Date());
            userDao.updateById(curUser);
            return curUser;
        }
            throw  new RuntimeException("登录失败");
    }
    @Override
    public int register(User user) {
        if(this.checkUserNameIsUnique(user)) {

            if (this.checkUserNameAndPassword(user)) {

                // 密码MD5加密
                user.setPassword(this.MD5Code(user.getPassword()));
                // 加入创建时间
                user.setCreateTime(new Date());
                // 入库
                int userdb = userDao.register(user);
                if (true) {
                    return userdb;
                }
                throw new RuntimeException("失败");
//            userMapper.insert(user);
//            return new ServiceRes(1, "注册成功");

            } else throw new RuntimeException("用户名或密码不合法");
        }else throw new RuntimeException("用户已存在");

    }

    public User upload (User user){
        User curUser = this.checkUserIsExit(user);
        if(curUser!=null) {

            // 更新用户最后登录时间
            curUser.setLoginTime(new Date());
            userDao.updateById(curUser);
            return curUser;
        }
        throw  new RuntimeException("登录失败");
    }


    private boolean checkUserNameIsUnique(User user) {
        return false;
    }

    private User checkUserIsExit(User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, user.getUsername());
        lqw.eq(User::getPassword, user.getPassword());
        return userDao.selectOne(lqw);
    }
private String MD5Code(String text) {
    return DigestUtils.md5DigestAsHex(text.getBytes(StandardCharsets.UTF_8));
}
    private Boolean checkUserNameAndPassword(User user) {
        String regex = "^[_a-z0-9A-Z]+$";
        return user.getUsername().matches(regex) && user.getPassword().matches(regex);
    }
}
