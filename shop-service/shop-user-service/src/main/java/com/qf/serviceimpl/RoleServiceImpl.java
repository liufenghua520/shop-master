package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.RoleMapper;
import com.qf.dao.RolePowerMapper;
import com.qf.entity.Role;
import com.qf.entity.RolePowerTable;
import com.qf.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/2 17:00
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePowerMapper rolePowerMapper;

    @Override
    public List<Role> listRole() {

        return roleMapper.selectList(null);
    }

    @Override
    public int addRole(Role role) {

        return roleMapper.insert(role);
    }

    @Override
    public List<Role> listRoleByUid(Integer uid) {

        return roleMapper.queryListByUid(uid);
    }

    @Override
    public int updateRolePower(Integer rid, Integer[] pids) {

        //根据角色删除该角色所拥有的权限
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("rid", rid);
        rolePowerMapper.delete(queryWrapper);

        //添加给该角色选择的权限
        for (Integer pid : pids) {
            rolePowerMapper.insert(new RolePowerTable(rid,pid));
        }

        return 1;
    }
}
