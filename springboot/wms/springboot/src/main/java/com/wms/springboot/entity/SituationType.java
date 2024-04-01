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
 * @since 2024-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SituationType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String situationType;

    private String remark;


}
