package com.wms.springboot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.springboot.entity.Storage;
import com.wms.springboot.mapper.StorageMapper;
import com.wms.springboot.service.StorageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author starisora
 * @since 2024-03-15
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {

}
