package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsService {
    List<Goods> queryGoods();

    Goods insertGoods(Goods goods);

    int updateGoodsType(Integer gid, Integer[] tids);
}
