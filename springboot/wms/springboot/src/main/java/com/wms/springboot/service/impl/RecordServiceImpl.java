package com.wms.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.springboot.mapper.RecordMapper;
import com.wms.springboot.entity.Record;
import com.wms.springboot.service.RecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author starisora
 * @since 2024-03-20
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

}
