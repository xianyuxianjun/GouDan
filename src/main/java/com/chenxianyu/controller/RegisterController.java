package com.chenxianyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenxianyu.entity.R;
import com.chenxianyu.entity.User;
import com.chenxianyu.service.IUserService;
import com.chenxianyu.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private IUserService userService;

    @PostMapping
    public R register(@RequestBody User user){
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("email",user.getEmail());
        User one = userService.getOne(query);
        if (one != null){
            return R.fail("用户已存在");
        }
        user.setPassword(MD5Util.toMD5(user.getPassword()));
        boolean save = userService.save(user);
        if (save){
            return R.success("注册成功");
        }
        return R.fail("注册失败");
    }

}
