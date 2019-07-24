package com.qf.service;

import com.qf.entity.Address;

import java.util.List;

public interface IAddressService {

    int insertAddress(Address address);
    List<Address> queryByUid(Integer uid);
    Address queryByAid(Integer aid);

}
