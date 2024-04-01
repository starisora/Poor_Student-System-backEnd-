package com.wms.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author starisora
 * @since 2024-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    /**
     * 货名
     */
    private String name;

    /**
     * 仓库
     */
    private int storage;

    /**
     * 分类
     */
    @TableField("goodsType")
    private int goodsType;

    /**
     * 数量
     */
    private int count;

    /**
     * 备注
     */
    private String remark;


}
