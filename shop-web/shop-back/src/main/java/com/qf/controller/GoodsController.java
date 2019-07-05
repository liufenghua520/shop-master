package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/5 14:23
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Value("${upload.path}")
    private String uploadPath;

    @Reference
    private IGoodsService goodsService;

    //商品列表展示
    @RequestMapping("/list")
    public String showGoodsList(Model model){
        List<Goods> goodsList = goodsService.queryGoods();
        model.addAttribute("goodsList",goodsList);
        return "goodslist";
    }

    /**
     * 上传文件（商品图片）
     * @param file
     * @return
     */
    @RequestMapping("/uploadImage")
    @ResponseBody
    public String uploadImg(MultipartFile file){

        String filepath = "";

        //截取原图片后缀
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String houzui = originalFilename.substring(index);

        //设置文件名称
        String filename = UUID.randomUUID().toString() + houzui;

        filepath = uploadPath + filename;

        try (
                InputStream is = file.getInputStream();
                OutputStream os = new FileOutputStream(filepath);
        ){
            IOUtils.copy(is,os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "{\"filepath\":\""+filepath+"\"}";
    }

    /**
     * 展示图片至前端页面
     * @param imagepath
     * @param response
     */

    @RequestMapping("/getImage")
    public void getImg(String imagepath, HttpServletResponse response){

        File file = new File(imagepath);

        try (
                InputStream is = new FileInputStream(file);
                OutputStream os = response.getOutputStream()
        ){
            IOUtils.copy(is,os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //添加商品
    @RequestMapping("/insert")
    public String insertGoods(Goods goods){
        goodsService.insertGoods(goods);
        return "redirect:/goods/list";
    }


}
