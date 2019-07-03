package com.qf.service;

import com.qf.entity.BackUser;

import java.util.List;

public interface IBackUserService {
    List<BackUser> queryAll();
    int addUser(BackUser backuser);

    int updateUserRole(Integer uid, Integer[] rid);

    BackUser loginCheck(String username,String password);
}
