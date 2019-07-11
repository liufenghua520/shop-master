package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.GoodsMapper;
import com.qf.dao.GoodsTypeTableMapper;
import com.qf.entity.Goods;
import com.qf.entity.GoodsTypeTable;
import com.qf.service.IGoodsService;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/5 14:43
 */
@Service
public class GoodsServiceimpl implements IGoodsService {

    @Reference
    private ISearchService searchService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsTypeTableMapper goodsTypeTableMapper;

    @Override
    public List<Goods> queryGoods() {
        return goodsMapper.selectList(null);
    }

    @Override
    public Goods insertGoods(Goods goods) {
        goodsMapper.insert(goods);
        searchService.add(goods);
        return goods;
    }

    @Override
    public int updateGoodsType(Integer gid, Integer[] tids) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gid",gid);
        goodsTypeTableMapper.delete(queryWrapper);

        for (Integer tid : tids) {
            goodsTypeTableMapper.insert(new GoodsTypeTable(gid,tid));
        }
        return 1;
    }
}
