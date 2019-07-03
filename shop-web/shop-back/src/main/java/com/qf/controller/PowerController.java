package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Power;
import com.qf.service.IPowerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/2 17:18
 */
@Controller
@RequestMapping("/power")
public class PowerController {

    @Reference
    private IPowerService powerService;

    @RequestMapping("/list")
    public String listPower(Model model){
        List<Power> powers = powerService.listPower();
        model.addAttribute("powers",powers);
        return "powerslist";
    }


    @RequestMapping("/listajax")
    @ResponseBody
    public List<Power> listPowerByAjax(){

        return powerService.listPower();
    }

    @RequestMapping("/insert")
    public String insertPower(Power power){
        powerService.insert(power);
        return "redirect:/power/list";
    }

    @ResponseBody
    @RequestMapping("/queryPowersByRid")
    public List<Power> queryPowersByRid(Integer rid){
        System.out.println("rid: "+rid);
        List<Power> powers = powerService.queryPowersByRid(rid);
//        System.out.println(powers);
        return powers;
    }
}
