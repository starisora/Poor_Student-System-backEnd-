package com.wms.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.springboot.service.GoodsTypeService;
import com.wms.springboot.entity.GoodsType;
import com.wms.springboot.mapper.GoodsTypeMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author starisora
 * @since 2024-03-18
 */
@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {

}
