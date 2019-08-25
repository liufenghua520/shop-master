package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    int insertOrderDetails(@Param("orderDetails") List<OrderDetail> orderDetails,@Param("tableIndex") int tableIndex);
}
