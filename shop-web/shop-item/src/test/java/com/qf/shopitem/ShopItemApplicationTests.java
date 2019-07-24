package com.qf.shopitem;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopItemApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    public void contextLoads() throws IOException, TemplateException {
        //获得模板对象
        Template template = configuration.getTemplate("hello.ftl");

        //准备模板中的数据
        Map<String,Object> map = new HashMap<>();
        map.put("key","World!");

        map.put("age",30);

        int array[] = {1,2,3,4,5,6,7};
        map.put("ages",array);

        map.put("date",new Date());

        template.process(map,new FileWriter("C:\\Users\\36043\\Desktop\\新建文件夹\\freemarker.html"));

    }

}
