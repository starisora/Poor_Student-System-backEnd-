package com.wms.springboot.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author starisora
 * @since 2024-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private int id;

    /**
     * 货品id
     */
    private int goods;

    /**
     * 取货人/补货人
     */
    private int user_id;

    /**
     * 操作人id
     */
    private int admin_id;

    /**
     * 数量
     */
    private int count;

    /**
     * 操作时间
     */
//    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;


}
