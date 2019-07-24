package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.OrderDetailMapper;
import com.qf.dao.OrderMapper;
import com.qf.entity.*;
import com.qf.order.OrderUtil;
import com.qf.service.IAddressService;
import com.qf.service.IOrderService;
import com.qf.service.IShopCartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 21:48
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Reference
    private IAddressService addressService;

    @Reference
    private IShopCartService cartService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderUtil orderUtil;


    @Override
    public List<Order> queryByUid(Integer uid) {
        return orderMapper.queryByUid(uid);
    }

    @Override
    public Order queryByOid(String oid) {
        return orderMapper.selectById(oid);
    }

    @Override
    public int insertOrder(Integer aid, User user) {

        //1.根据地址id——aid查询地址信息
        Address address = addressService.queryByAid(aid);

        //2.根据用户查询购物车信息
        List<ShopCart> carts = cartService.queryCartList(user, null);
            //计算购物车总价
        BigDecimal bigDecimal = BigDecimal.valueOf(0.0);
        for (ShopCart shopCart : carts) {
            bigDecimal = bigDecimal.add(shopCart.getSprice());
        }

        //3.封装订单对象：
        Order order = new Order();
        order.setOrderid(orderUtil.createOrderId(user.getId()));
        order.setUid(user.getId());
        order.setAddress(address.getAddress());
        order.setPerson(address.getPerson());
        order.setPhone(address.getPhone());
        order.setAllprice(bigDecimal);
        order.setStatus(0);

        //4.封装订单详情对象
        List<OrderDetail> orderDetails = new ArrayList<>();
        int i = 0;
        for (ShopCart cart : carts) {
            OrderDetail orderDetail = new OrderDetail(
                null,order.getOrderid(),
                    cart.getGid(),
                    cart.getGoods().getGname(),
                    cart.getGoods().getGprice(),
                    cart.getGoods().getGimage(),
                    cart.getGnumber(),
                    cart.getSprice()
            );
            orderDetails.add(orderDetail);
            i ++ ;
            if (i%1000==0 || i == carts.size()){

                //批量插入订单详情
                orderDetailMapper.insertOrderDetails(orderDetails);
                orderDetails.clear();
            }

        }
        //5.插入订单
        orderMapper.insert(order);

        return 1;
    }
}
