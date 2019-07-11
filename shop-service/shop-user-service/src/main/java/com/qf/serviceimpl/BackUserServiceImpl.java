package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.dao.BackUserMapper;
import com.qf.dao.UserRoleMapper;
import com.qf.entity.BackUser;
import com.qf.entity.UserRoleTable;
import com.qf.service.IBackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/1 22:12
 */
@Service
public class BackUserServiceImpl implements IBackUserService {

    @Autowired
    private BackUserMapper backUserMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<BackUser> queryAll() {
        
        return  backUserMapper.selectList(null);
    }

    @Override
    public int addUser(BackUser backuser) {
        return backUserMapper.insert(backuser);
    }

    @Override
    public int deleteUser(Integer id) {
        return backUserMapper.deleteById(id);
    }

    @Override
    public BackUser queryById(Integer id) {
        BackUser user = backUserMapper.selectById(id);
        return user;
    }

    @Override
    public int updateUser(BackUser backUser) {

        return backUserMapper.updateById(backUser);
    }

    @Override
    public int updateUserRole(Integer uid, Integer[] rid) {

        //根据用户id删除用户和角色的所有关系
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        userRoleMapper.delete(queryWrapper);

        //给用户插入选择的角色
        for (Integer roleid : rid) {
            UserRoleTable userRoleTable = new UserRoleTable(uid,roleid);
            userRoleMapper.insert(userRoleTable);
        }

        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BackUser user = backUserMapper.queryUserByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("sorry,该用户不存在");
        }
        return user;
    }

   /* @Override
    public BackUser loginCheck( String username,String password) {
        BackUser user = backUserMapper.queryUserByUsername(username);
        if (user!=null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }*/
}
