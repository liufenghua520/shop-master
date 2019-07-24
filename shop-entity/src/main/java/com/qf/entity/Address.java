package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 19:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private String person;
    private String phone;
    private String address;
    private Integer isdefault = 0;
    private Date createtime = new Date();

}
