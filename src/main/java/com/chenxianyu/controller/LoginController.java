package com.chenxianyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenxianyu.entity.R;
import com.chenxianyu.entity.User;
import com.chenxianyu.service.IUserService;
import com.chenxianyu.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public R login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("email",email);
        User queryUser = userService.getOne(query);
        if (queryUser != null){
            if (queryUser.getPassword().equals(MD5Util.toMD5(password))){
                return R.success(queryUser);
            }
            return R.fail("密码错误");
        }
        return R.fail("用户不存在");
    }
}
