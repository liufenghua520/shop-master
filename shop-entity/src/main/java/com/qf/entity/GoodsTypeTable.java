package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/6 21:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsTypeTable implements Serializable {
    private Integer gid;
    private Integer tid;
}
