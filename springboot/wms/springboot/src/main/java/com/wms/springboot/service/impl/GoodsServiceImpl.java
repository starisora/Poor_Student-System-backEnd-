package com.wms.springboot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.springboot.entity.Goods;
import com.wms.springboot.service.GoodsService;
import com.wms.springboot.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author starisora
 * @since 2024-03-19
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

}
