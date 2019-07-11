package com.qf.service;

import com.qf.entity.Power;

import java.util.List;

public interface IPowerService {
    List<Power> listPower();

    int insert(Power power);

    List<Power> queryPowersByRid(Integer rid);

    int deletePower(Integer id);
}
