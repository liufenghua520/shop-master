package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.BackUser;
import com.qf.service.IBackUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("loginUser")
public class LoginController {

    @Reference
    private IBackUserService backUserService;

    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String logincheck(String username, String password, Model model){
        BackUser user = backUserService.loginCheck(username,password);
        System.out.println(user);
        if (user!=null){
            model.addAttribute("loginUser",user);
            return "index";
        }
        return "redirect:/tologin?error=1";
    }
}
