package com.chenxianyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenxianyu.entity.R;
import com.chenxianyu.entity.User;
import com.chenxianyu.service.IUserService;
import com.chenxianyu.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author 陈咸鱼
 * @since 2024-12-10
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;


    @PostMapping("/list")
    public R List(){
        List<User> list = userService.list();
        return R.success(list);
    }

    @PostMapping("get")
    public R get(@RequestBody User user){
        User user1 = userService.getById(user.getUserId());
        return R.success(user1);
    }

    @PostMapping("/update")
    public R update(@RequestBody User user){
        boolean b = userService.updateById(user);
        if (b){
            return R.success("修改成功");
        }
        return R.fail("修改失败");
    }

    @PostMapping("/detect")
    public R detect(@RequestBody User user){
        boolean b = userService.removeById(user.getUserId());
        if (b){
            return R.success("删除成功");
        }
        return R.fail("删除失败");
    }

    @PostMapping("/add")
    public R add(@RequestBody User user){
        String email = user.getEmail();
        if (userService.getOne(new QueryWrapper<User>().eq("email", email)) != null){
            return R.fail("邮箱已被注册");
        }
        user.setPassword(MD5Util.toMD5("123456"));
        Random random = new Random();
        long min = 1_000_000_000L; // 10位数的最小值 10^9
        long max = 9_999_999_999L; // 10位数的最大值 10^10 - 1
        long num = min + (long)(random.nextDouble() * (max - min + 1));
        user.setUserId(String.valueOf(num));
        boolean save = userService.save(user);
        if (save){
            return R.success("添加成功");
        }
        return R.fail("添加失败");
    }

}
