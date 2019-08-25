package com.qf.service;

import com.qf.entity.Order;
import com.qf.entity.User;

import java.util.List;

public interface IOrderService {
    List<Order> queryByUid(Integer uid);

    Order queryByOid(String oid);

    Order insertOrder(Integer aid, User user);

    int updateOrderStatus(String orderid, int status);
}
