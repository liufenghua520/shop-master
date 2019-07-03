package com.qf.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.BackUser;

public interface BackUserMapper  extends BaseMapper<BackUser> {
    BackUser queryUserByUsername(String username);
}
