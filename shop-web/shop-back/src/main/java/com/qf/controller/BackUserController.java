package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.BackUser;
import com.qf.service.IBackUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/1 20:49
 */
@Controller
@RequestMapping("/buser")
public class BackUserController {

    @Reference
    private IBackUserService backUserService;

    @RequestMapping("/list")
    public String queryAll(Model model){
        List<BackUser> backUsers = backUserService.queryAll();
        model.addAttribute("users",backUsers);
        return "backuserlist";
    }

    @RequestMapping("/addUser")
    public String addUser(BackUser backuser){
        backUserService.addUser(backuser);
        return "redirect:/buser/list";
    }

    @RequestMapping("/delete")
    private String deleteUser(Integer id){
        backUserService.deleteUser(id);
        return "redirect:/buser/list";
    }

    @RequestMapping("/toupdate")
    public String toUpdate(Integer id,Model model){
        BackUser backUser = backUserService.queryById(id);
        model.addAttribute("user",backUser);
        return "updateuser";
    }

    @RequestMapping("/update")
    public String updateUser(BackUser backUser){
        System.out.println("backuser: "+backUser);
        backUserService.updateUser(backUser);

        return "redirect:/buser/list";
    }


    @RequestMapping("/updaterole")
    public String updateUserRole(Integer uid,Integer[] rid){

        backUserService.updateUserRole(uid, rid);
        return "redirect:/buser/list";
    }
}
