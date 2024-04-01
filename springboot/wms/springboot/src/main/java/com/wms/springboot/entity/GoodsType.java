package com.wms.springboot.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author starisora
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("goods_type")
public class GoodsType implements Serializable {

//    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private int id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;


}
