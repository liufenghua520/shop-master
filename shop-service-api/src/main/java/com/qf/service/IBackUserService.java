package com.qf.service;

import com.qf.entity.BackUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IBackUserService extends UserDetailsService {
    List<BackUser> queryAll();
    int addUser(BackUser backuser);

    int updateUserRole(Integer uid, Integer[] rid);

    int deleteUser(Integer id);

    BackUser queryById(Integer id);

    int updateUser(BackUser backUser);

//    BackUser loginCheck(String username,String password);
}
