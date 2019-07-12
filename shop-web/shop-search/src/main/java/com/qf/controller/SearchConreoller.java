package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/11 16:47
 */
@RequestMapping("/search")
@Controller
public class SearchConreoller {

    @Reference
    private ISearchService searchService;


    @RequestMapping("/searchByKey")
    public String searchByKey(String keyword, Model model){

        System.out.println("搜索的关键字："+keyword);
        List<Goods> goodsList = searchService.searchBykey(keyword);
        model.addAttribute("goods",goodsList);
        System.out.println(goodsList);
        return "searchlist";
    }


}
