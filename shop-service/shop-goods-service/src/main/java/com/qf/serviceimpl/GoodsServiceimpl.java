package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.GoodsMapper;
import com.qf.dao.GoodsTypeTableMapper;
import com.qf.entity.Goods;
import com.qf.entity.GoodsTypeTable;
import com.qf.network.HttpUtil;
import com.qf.service.IGoodsService;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private RabbitTemplate rabbitTemplate;

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
        //添加商品至数据库
        goodsMapper.insert(goods);

//        //将商品信息同步添加至索引库
//        searchService.add(goods);
//
//        //创建商品详情页
//        HttpUtil.sendGet("http://localhost:8083/item/createhtml?gid="+goods.getId());

        rabbitTemplate.convertAndSend("goods_exchange","",goods);

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

    @Override
    public Goods queryById(Integer gid) {
        return goodsMapper.selectById(gid);
    }

    @Override
    public List<Goods> queryByType(Integer tid) {
        return goodsMapper.queryByType(tid);
    }
}
