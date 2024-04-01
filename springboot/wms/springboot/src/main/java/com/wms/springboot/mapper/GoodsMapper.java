package com.wms.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.springboot.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author starisora
 * @since 2024-03-19
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

}
