package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.GoodsMapper;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/5 14:43
 */
@Service
public class GoodsServiceimpl implements IGoodsService {


    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> queryGoods() {
        return goodsMapper.selectList(null);
    }

    @Override
    public Goods insertGoods(Goods goods) {
        goodsMapper.insert(goods);
        return goods;
    }
}
