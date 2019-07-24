package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.AddressMapper;
import com.qf.entity.Address;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 19:45
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public int insertAddress(Address address) {
        return addressMapper.insert(address);
    }

    @Override
    public List<Address> queryByUid(Integer uid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("uid",uid);
        return addressMapper.selectList(wrapper);
    }

    @Override
    public Address queryByAid(Integer aid) {

        return addressMapper.selectById(aid);
    }
}
