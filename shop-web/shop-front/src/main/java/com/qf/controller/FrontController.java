package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.GoodsType;
import com.qf.service.IGoodTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/14 17:15
 */
@Controller
public class FrontController {

    @Reference
    private IGoodTypeService goodTypeService;

    @RequestMapping("/")
    public String goodsTypeList(Model model){
        List<GoodsType> goodsTypes = goodTypeService.goodsTypeList();
        model.addAttribute("gtlist",goodsTypes);
        return "index";
    }
}
