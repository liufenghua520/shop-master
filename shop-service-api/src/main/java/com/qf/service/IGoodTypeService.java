package com.qf.service;

import com.qf.entity.GoodsType;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/5 19:45
 */
public interface IGoodTypeService {
    List<GoodsType> goodsTypeList();

    GoodsType insertGoodsType(GoodsType goodsType);

    int deleteGoodsType(Integer id);


    List<GoodsType> queryTypesByGid(Integer gid);
}
