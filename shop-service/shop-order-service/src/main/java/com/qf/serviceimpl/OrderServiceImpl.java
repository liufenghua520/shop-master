package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.OrderDetailMapper;
import com.qf.dao.OrderMapper;
import com.qf.dataconfig.DynamicDataSource;
import com.qf.entity.*;
import com.qf.order.OrderUtil;
import com.qf.service.IAddressService;
import com.qf.service.IOrderService;
import com.qf.service.IShopCartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
        //确定数据源下标，定位到数据库
        int uids = Integer.parseInt(orderUtil.getUid(uid));
        int dbIndex = uids % 2 + 1;
        DynamicDataSource.set("orderdb"+dbIndex);
        System.out.println("定位到的数据库id："+dbIndex);

        //计算表的下标，确定表的位置：
        int tableIndex = uids / 2 % 2 + 1;
        System.out.println("定位到表的位置："+tableIndex);

        return orderMapper.queryByUid(tableIndex,uid);
    }

    @Override
    public Order queryByOid(String oid) {
        //根据订单id解析用户id后四位
        Integer uids = Integer.parseInt(oid.substring(6,10));

        //确定数据源，定位到数据库
        int dbIndex = uids % 2 + 1;
        DynamicDataSource.set("orderdb"+dbIndex);
        System.out.println("定位到的数据库id："+dbIndex);

        //确定表的位置：
        int tableIndex = uids / 2 % 2 + 1;
        System.out.println("定位到表的位置："+tableIndex);

        return orderMapper.selectByOid(oid,tableIndex);
    }

    @Override
    public Order insertOrder(Integer aid, User user) {

        //确定数据源，定位到数据库
        int uids = Integer.parseInt(orderUtil.getUid(user.getId()));
        int dbIndex = uids % 2 + 1 ;
        System.out.println("定位到的数据库id："+dbIndex);
        DynamicDataSource.set("orderdb"+dbIndex);

        //确定表的位置：
        int tableIndex = uids / 2 % 2 + 1;
        System.out.println("定位到表的位置："+tableIndex);

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
        Order order = new Order(
                orderUtil.createOrderId(user.getId()),
                user.getId(),
                address.getPerson(),
                address.getAddress(),
                address.getPhone(),
                bigDecimal,
                new Date(),
                0,
                null
        );

        System.out.println("order对象:"+order);

        //4.封装订单详情对象
        List<OrderDetail> orderDetails = new ArrayList<>();
        int i = 0;
        for (ShopCart cart : carts) {
            OrderDetail orderDetail = new OrderDetail(
                null,
                    order.getOrderid(),
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

                //5.批量插入订单详情
                orderDetailMapper.insertOrderDetails(orderDetails,tableIndex);
                orderDetails.clear();
            }

        }
        //6.插入订单
        orderMapper.insertOrder(order,tableIndex);

        //7.清空购物车
        cartService.deleteCart(user);

        return order;
    }

    @Override
    public int updateOrderStatus(String orderid, int status) {

        //根据订单id解析用户id后四位
        Integer uids = Integer.parseInt(orderid.substring(6,10));

        //确定数据源，定位到数据库
        int dbIndex = uids % 2 + 1;
        DynamicDataSource.set("orderdb"+dbIndex);
        System.out.println("定位到的数据库id："+dbIndex);

        //确定表的位置：
        int tableIndex = uids / 2 % 2 + 1;
        System.out.println("定位到表的位置："+tableIndex);

        return orderMapper.updateOrderStatus(orderid,status,tableIndex);
    }
}
