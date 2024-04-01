package com.wms.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.springboot.entity.Record;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author starisora
 * @since 2024-03-20
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

}
