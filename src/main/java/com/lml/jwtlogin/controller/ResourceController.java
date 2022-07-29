package com.lml.jwtlogin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lml.jwtlogin.dao.UserDao;
import com.lml.jwtlogin.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//import static java.awt.Container.log;

@Slf4j
@RestController
// 上传文件功能
public class ResourceController {
    @Value("${file.imgPath}")

    private String imgPath;
    @Autowired
    private UserDao userDao;
    // 上传图片
    @PostMapping("/user/uploadImg")
    public Map<String,String> uploadImg(@RequestParam("file") MultipartFile img, User user) throws IOException {
        Map<String, String> map=new HashMap<>();
        try{ // 获取文件后缀
            User curUser = this.checkUserIsExit(user);
            if(curUser!=null) {
                String fileName = img.getOriginalFilename();
                System.out.println(fileName);
                String stffixName = fileName.substring(fileName.lastIndexOf("."));
                // 重命名文件
                UUID uuid = UUID.randomUUID();
                String finalName = uuid + stffixName;
                // 当文件名重复 可能性极小
                while( new File(finalName).exists()){
                    uuid = UUID.randomUUID();
                    finalName = uuid + stffixName;
                }
                log.info("用户名："+user.getUsername());
                // 文件保存
                String finalPath = imgPath + File.separator + finalName;
                if(finalPath!=null){
                    img.transferTo(new File(finalPath));
                    curUser.setAvata("/static/"+finalName);
                    userDao.updateById(curUser);

                    map.put("msg","上传成功");
                    map.put("code","200");
                }else {
                    map.put("msg","上传失败");
                    map.put("code","401");
                }

            }else {
                map.put("msg","上传失败");
                map.put("code","401");
            }
//            return map;
        }catch (Exception ex){
            map.put("msg", String.valueOf(ex));
        }
        return map;
    }
    private User checkUserIsExit(User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, user.getUsername());
        return userDao.selectOne(lqw);
    }
}

