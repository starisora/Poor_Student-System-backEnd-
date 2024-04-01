package com.wms.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.springboot.common.QueryPageParam;
import com.wms.springboot.common.Result;
import com.wms.springboot.entity.Record;
import com.wms.springboot.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author starisora
 * @since 2024-03-20
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;
    //分页
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam){
        //其它数据
        HashMap param = queryPageParam.getParam();
//        System.out.println(param);

        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        String roleId = (String) param.get("roleId");

        Page<Record> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
//            lambdaQueryWrapper.like(Record::getName,(String)param.get("name"));
        }

        if (StringUtils.isNotBlank(sex) && !"null".equals(sex)){
//            lambdaQueryWrapper.eq(Record::getSex,(String)param.get("sex"));
        }

        if (StringUtils.isNotBlank(roleId) && !"null".equals(roleId)){
//            lambdaQueryWrapper.eq(Record::getRoleId,(String)param.get("roleId"));
        }


        IPage result = recordService.page(page,lambdaQueryWrapper);

        System.out.println("total==="+result.getRecords());
        return Result.result(200,"成功",result.getTotal(),result.getRecords());
        //return Result.result(200,"成功",0L,result.getRecords());
    }
}
