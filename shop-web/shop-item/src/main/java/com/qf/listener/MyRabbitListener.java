package com.qf.listener;

import com.qf.controller.ItemController;
import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/13 17:39
 */
@Component
public class MyRabbitListener {

    @Autowired
    private Configuration configuration;

    @RabbitListener(queues = "item_queue")
    public void msgHandle(Goods goods) throws IOException {
        Template template = configuration.getTemplate("goodsitem.ftl");

        Map<String ,Object> map = new HashMap<>();
        map.put("goods",goods);
        map.put("images",goods.getGimage().split("\\|"));
        map.put("contextPath","");

        String path = ItemController.class.getResource("/static").getPath().replaceAll("%20"," ");
        System.out.println("ClassPath路径为: "+path);
        File file = new File(path+"/page");
        if (!file.exists()){
            file.mkdirs();
        }
        //根据free marker生成商品详情静态页面
        try {
            template.process(map,new FileWriter(file.getAbsolutePath()+"/"+goods.getId()+".html"));
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
