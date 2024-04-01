package com.wms.springboot.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author starisora
 * @since 2024-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Dormitory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private int id;

    /**
     * 楼层号
     */
    private String floorNum;

    /**
     * 宿舍号
     */
    private int num;

    /**
     * 室长名
     */
    private String dormitoryDirector;


}
