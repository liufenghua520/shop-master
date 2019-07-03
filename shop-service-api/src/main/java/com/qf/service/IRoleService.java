package com.qf.service;

import com.qf.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role>  listRole();

    int addRole(Role role);

    List<Role> listRoleByUid(Integer uid);

    int updateRolePower(Integer rid, Integer[] pids);
}
