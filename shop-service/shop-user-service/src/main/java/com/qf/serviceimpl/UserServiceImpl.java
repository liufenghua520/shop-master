package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.password.BCryptUtil;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/19 21:25
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int register(User user) {
        //判断用户名是否唯一
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        User u = userMapper.selectOne(queryWrapper);
        if (u!=null){
            return -2;//用户名已存在
        }
        //判断邮箱是否唯一
        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("username",user.getUsername());
        User u2 = userMapper.selectOne(queryWrapper);
        if (u2!=null){
            return -3;//邮箱已存在
        }
        //密码加密
        user.setPassword(BCryptUtil.hashPassword(user.getPassword()));

        //进行注册
        return userMapper.insert(user);
    }

    @Override
    public User queryByUserName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);

        return user;
    }

    @Override
    public int updatePassword(String username, String password) {
        User user = queryByUserName(username);
        user.setPassword(BCryptUtil.hashPassword(password));

        return userMapper.updateById(user);
    }

    @Override
    public User login(User user) {
        User u = queryByUserName(user.getUsername());
        if (u!=null){
            boolean flag = BCryptUtil.checkPassword(user.getPassword(),u.getPassword());
            if (flag){
                return u;
            }
        }
        return null;
    }
}
