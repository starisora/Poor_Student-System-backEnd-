package com.wms.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.springboot.common.QueryPageParam;
import com.wms.springboot.common.Result;
import com.wms.springboot.entity.SituationType;
import com.wms.springboot.service.SituationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author starisora
 * @since 2024-03-24
 */
@RestController
@RequestMapping("/situationType")
public class SituationTypeController {
    @Autowired
    private SituationTypeService situationTypeService;

    //查询
    @PostMapping("/list")
    public Result list(){
        List list = situationTypeService.lambdaQuery().list();
        //System.out.println(list);
        return Result.result(200,"成功",0L,list);
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody SituationType situationType){
        System.out.println(situationType);
        try{
            return Result.result(200,"成功",0L,situationTypeService.save(situationType));
        }catch (Exception e){
            return Result.fail();
        }
        //return situationTypeService.save(SituationType)?Result.result(200,"成功",0L,true):Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody SituationType situationType){
        //return situationTypeService.updateById(SituationType);
        return situationTypeService.updateById(situationType)?Result.success():Result.fail();
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody SituationType situationType){
        return situationTypeService.saveOrUpdate(situationType);
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
//        return situationTypeService.removeById(id);
        System.out.println(id);
        return situationTypeService.removeById(id)?Result.success():Result.fail();
    }
    //查询(模糊、匹配)
    @PostMapping("/listP")
    public Result listP(@RequestBody SituationType situationType){
        LambdaQueryWrapper<SituationType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        //判断是否是空,不是就执行if内容
//        if(StringUtils.isNotBlank(situationType.getName())){
//            //like模糊匹配,eq是匹配
//            lambdaQueryWrapper.like(SituationType::getName,situationType.getName());
//        }
        //返回值类型是List<SituationType>生效
        //return situationTypeService.list(lambdaQueryWrapper);
        return Result.result(200,"成功",0L,situationTypeService.list(lambdaQueryWrapper));
    }

    //分页
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam){
        //其它数据
        HashMap param = queryPageParam.getParam();

//        String name = (String) param.get("name");
//        String storage = (String) param.get("storage");
//        String situationTypeType = (String) param.get("situationTypeType");

        Page<SituationType> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<SituationType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
//            lambdaQueryWrapper.like(SituationType::getName,(String)param.get("name"));
//        }
//        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)){
//            lambdaQueryWrapper.like(SituationType::getStorage,(String)param.get("storage"));
//        }
//        if (StringUtils.isNotBlank(situationTypeType) && !"null".equals(situationTypeType)){
//            lambdaQueryWrapper.like(SituationType::getSituationTypeType,(String)param.get("situationTypeType"));
//        }


        IPage result = situationTypeService.page(page,lambdaQueryWrapper);

        System.out.println("total==="+result.getTotal());
        return Result.result(200,"成功",result.getTotal(),result.getRecords());
        //return Result.result(200,"成功",0L,result.getRecords());
    }

}
