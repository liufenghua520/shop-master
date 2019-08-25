package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.Order;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import com.qf.service.IOrderService;
import com.qf.service.IShopCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 19:03
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference
    private IOrderService orderService;

    @Reference
    private IAddressService addressService;

    @Reference
    private IShopCartService cartService;

    @IsLogin(mustLogin = true)
    @RequestMapping("/edit")
    public String toOrderEdit(User user, Model model){
        //查询购物车的信息
        List<ShopCart> cartList = cartService.queryCartList(user, null);

        //计算购物车总价
        BigDecimal bigDecimal = BigDecimal.valueOf(0.0);
        for (ShopCart shopCart : cartList) {
            bigDecimal = bigDecimal.add(shopCart.getSprice());
        }

        //查询用户的收获地址信息
        List<Address> addresses = addressService.queryByUid(user.getId());

        model.addAttribute("addresses",addresses);
        model.addAttribute("carts",cartList);
        model.addAttribute("sumPrice",bigDecimal.doubleValue());

        return "orderedit";
    }

    @IsLogin(mustLogin = true)
    @RequestMapping("/insert")
    public String insertOrder(Integer aid,User user){

        //插入订单
        Order order = orderService.insertOrder(aid, user);
        return "redirect:/pay/alipay?orderid="+order.getOrderid();
    }

    @IsLogin(mustLogin = true)
    @RequestMapping("/list")
    public String listOrders(User user,Model model){

        List<Order> orders = orderService.queryByUid(user.getId());

        model.addAttribute("orders",orders);
        return "orderlist";
    }
}
