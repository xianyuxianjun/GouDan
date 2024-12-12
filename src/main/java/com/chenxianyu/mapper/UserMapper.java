package com.chenxianyu.mapper;

import com.chenxianyu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author chenxianyu
 * @since 2024-12-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
