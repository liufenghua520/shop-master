package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    List<Order> queryByUid(@Param("tableIndex") int tableIndex, @Param("uid") Integer uid);

    Order selectByOid(@Param("oid") String oid, @Param("tableIndex") int tableIndex);

    int insertOrder(@Param("order") Order order, @Param("tableIndex") int tableIndex);

    int updateOrderStatus(@Param("orderid") String orderid, @Param("tableIndex") int tableIndex, @Param("status") int status);
}
