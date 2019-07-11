package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.GoodsType;

import java.util.List;

public interface GoodsTypeMapper extends BaseMapper<GoodsType> {
    List<GoodsType> queryList();

    List<GoodsType> queryTypesByGid(Integer gid);
}
