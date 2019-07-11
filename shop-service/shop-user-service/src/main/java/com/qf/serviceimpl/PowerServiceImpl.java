package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.dao.PowerMapper;
import com.qf.entity.Power;
import com.qf.service.IPowerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/2 17:20
 */
@Service
public class PowerServiceImpl implements IPowerService {

    @Autowired
    private PowerMapper powerMapper;

    @Override
    public List<Power> listPower() {
        return powerMapper.queryPowers();
    }

    @Override
    public int insert(Power power) {
        return powerMapper.insert(power);
    }

    @Override
    public List<Power> queryPowersByRid(Integer rid) {
        return powerMapper.queryPowersByRid(rid);
    }

    @Override
    public int deletePower(Integer id) {
        return powerMapper.deleteById(id);
    }


}
