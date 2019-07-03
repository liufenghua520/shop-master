package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Role;
import com.qf.service.IRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/2 16:42
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Reference
    private IRoleService roleService;

    @RequestMapping("/list")
    public String listRole(Model model) {
        List<Role> roles = roleService.listRole();
        model.addAttribute("roles", roles);
        return "rolelist";
    }

    @RequestMapping("/insert")
    public String insertRole(Role role) {
        roleService.addRole(role);
        return "redirect:/role/list";
    }

    @ResponseBody
    @RequestMapping("/listajax")
    public List<Role> ListRoleByAjax(Integer uid) {

        List<Role> roles = roleService.listRoleByUid(uid);
        return roles;
    }

    @RequestMapping("/updatePower")
    @ResponseBody
    public String updateRolePower(Integer rid, @RequestParam("pids[]") Integer[] pids) {
        System.out.println("角色id："+rid+"  选择的权限id："+ Arrays.toString(pids));
        roleService.updateRolePower(rid, pids);
        return "succ";
    }
}
