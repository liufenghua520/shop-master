package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 20:24
 */
@Controller
@RequestMapping("/address")
public class AddressController {

    @Reference
    private IAddressService addressService;

    @IsLogin(mustLogin = true)
    @RequestMapping("/insert")
    public String insertAddress(Address address, User user){

        address.setUid(user.getId());
        addressService.insertAddress(address);

        return "redirect:/order/edit";
    }

}
