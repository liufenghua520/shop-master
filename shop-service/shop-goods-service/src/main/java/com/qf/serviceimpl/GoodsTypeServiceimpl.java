package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.GoodsTypeMapper;
import com.qf.entity.GoodsType;
import com.qf.service.IGoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/5 19:46
 */
@Service
public class GoodsTypeServiceimpl implements IGoodTypeService {

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public List<GoodsType> goodsTypeList() {
        return goodsTypeMapper.selectList(null);
    }

    @Override
    public GoodsType insertGoodsType(GoodsType goodsType) {
        goodsTypeMapper.insert(goodsType);
        return goodsType;
    }
}
