package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/2 21:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePowerTable implements Serializable {

    private Integer rid;
    private Integer pid;
}
