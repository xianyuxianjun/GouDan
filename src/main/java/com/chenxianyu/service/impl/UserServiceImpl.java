package com.chenxianyu.service.impl;

import com.chenxianyu.entity.User;
import com.chenxianyu.mapper.UserMapper;
import com.chenxianyu.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author chenxianyu
 * @since 2024-12-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
