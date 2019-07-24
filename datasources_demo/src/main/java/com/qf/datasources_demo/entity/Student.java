package com.qf.datasources_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 0:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private Integer id;
    private String sname;
    private Integer sage;
    private String sex;
    private Date createtime;
}
