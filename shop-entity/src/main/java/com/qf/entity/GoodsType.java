package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/5 19:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsType implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String gtname;
    private Integer status;

    @TableField(exist = false)
    private String gtpname;

    @TableField(exist = false)
    private boolean checked;
}
