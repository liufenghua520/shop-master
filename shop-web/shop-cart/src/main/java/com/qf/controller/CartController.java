package com.qf.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.aop.IsLogin;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.IShopCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/21 14:26
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference
    private IShopCartService shopCartService;

    /**
     * 添加购物车：自定义注解+AOP
     * @return
     */
    @IsLogin
    @RequestMapping("/insert")
    public String addCart(ShopCart cart, User user, HttpServletResponse response,
                          @CookieValue(value = "cartToken",required = false)String cartToken){
        if (cartToken==null){
            cartToken = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("cartToken",cartToken);
            cookie.setMaxAge(60 * 60 * 24 * 365);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        //添加购物车
        shopCartService.insert(cart,user,cartToken);

        return "addsucc";
    }

    @IsLogin
    @ResponseBody
    @RequestMapping("/list")
    public String list(@CookieValue(value = "cartToken",required = false)String cartToken,
                       User user,String callback){

        List<ShopCart> cartList = shopCartService.queryCartList(user, cartToken);

        return callback != null ? callback+"("+ JSON.toJSONString(cartList) +")" : JSON.toJSONString(cartList);
    }

    @IsLogin
    @RequestMapping("/showCartPage")
    public String showCartPage(@CookieValue(value = "cartToken",required = false)String cartToken,
                               User user, Model model){

        //查看当前购物车商品信息
        List<ShopCart> cartList = shopCartService.queryCartList(user, cartToken);

        //计算总价
        BigDecimal bigDecimal = BigDecimal.valueOf(0.0);
        for (ShopCart shopCart : cartList) {
            bigDecimal = bigDecimal.add(shopCart.getSprice());
        }

        model.addAttribute("cartList",cartList);
        model.addAttribute("sumPrice",bigDecimal.doubleValue());

        return "shopcart";
    }
}
