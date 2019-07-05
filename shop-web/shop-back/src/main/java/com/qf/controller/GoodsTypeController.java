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
 * @date 2019/7/5 19:50
 */
@Controller
@RequestMapping("/gtype")
public class GoodsTypeController {

    @Reference
    private IGoodTypeService gtypeService;

    @RequestMapping("/list")
    public String goodstypeList(Model model){
        List<GoodsType> goodsTypes = gtypeService.goodsTypeList();

        model.addAttribute("gtypes", goodsTypes);
        return "gtypelist";
    }

    @RequestMapping("/insert")
    public String insertGoodsType(GoodsType goodsType){

        gtypeService.insertGoodsType(goodsType);
        return "redirect:/gtype/list";
    }

    @RequestMapping("/listajax")
    @ResponseBody
    public List<GoodsType> listajax(){

        return gtypeService.goodsTypeList();
    }

}
