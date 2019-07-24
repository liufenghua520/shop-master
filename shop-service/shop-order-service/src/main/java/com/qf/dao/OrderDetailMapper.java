package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.OrderDetail;

import java.util.List;

public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    int insertOrderDetails(List<OrderDetail> orderDetails);
}
