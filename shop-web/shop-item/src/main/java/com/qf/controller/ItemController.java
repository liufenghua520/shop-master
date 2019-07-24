package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/12 21:38
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private Configuration configuration;

    @RequestMapping("/createhtml")
    public void createHtml(Integer gid, HttpServletRequest request) throws IOException {
        //根据商品id查询商品详细信息
        Goods goods = goodsService.queryById(gid);

        Template template = configuration.getTemplate("goodsitem.ftl");

        Map<String ,Object> map = new HashMap<>();
        map.put("goods",goods);
        map.put("images",goods.getGimage().split("\\|"));
        map.put("contextPath",request.getContextPath());

        String path = ItemController.class.getResource("/static").getPath().replaceAll("%20"," ");
        System.out.println("ClassPath路径为: "+path);
        File file = new File(path+"/page");
        if (!file.exists()){
            file.mkdirs();
        }
        //根据free marker生成商品详情静态页面
        try {
            template.process(map,new FileWriter(file.getAbsolutePath()+"/"+gid+".html"));
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
