package com.wms.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.springboot.common.QueryPageParam;
import com.wms.springboot.common.Result;
import com.wms.springboot.entity.Menu;
import com.wms.springboot.entity.Storage;
import com.wms.springboot.service.StorageService;
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
 * @since 2024-03-15
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    //查询
    @GetMapping("/list")
    public Result list(){
        List list = storageService.lambdaQuery().list();
        //System.out.println(list);
        return Result.result(200,"成功",0L,list);
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage){
        System.out.println(storage);
        try{
            return Result.result(200,"成功",0L,storageService.save(storage));
        }catch (Exception e){
            return Result.fail();
        }
        //return storageService.save(Storage)?Result.result(200,"成功",0L,true):Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody Storage storage){
        //return storageService.updateById(Storage);
        return storageService.updateById(storage)?Result.success():Result.fail();
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Storage storage){
        return storageService.saveOrUpdate(storage);
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
//        return storageService.removeById(id);
        System.out.println(id);
        return storageService.removeById(id)?Result.success():Result.fail();
    }
    //查询(模糊、匹配)
    @PostMapping("/listP")
    public Result listP(@RequestBody Storage storage){
        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判断是否是空,不是就执行if内容
        if(StringUtils.isNotBlank(storage.getName())){
            //like模糊匹配,eq是匹配
            lambdaQueryWrapper.like(Storage::getName,storage.getName());
        }
        //返回值类型是List<Storage>生效
        //return storageService.list(lambdaQueryWrapper);
        return Result.result(200,"成功",0L,storageService.list(lambdaQueryWrapper));
    }

    //分页
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam){
        //其它数据
        HashMap param = queryPageParam.getParam();

        String name = (String) param.get("name");

        Page<Storage> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Storage::getName,(String)param.get("name"));
        }


        IPage result = storageService.page(page,lambdaQueryWrapper);

        System.out.println("total==="+result.getTotal());
        return Result.result(200,"成功",result.getTotal(),result.getRecords());
        //return Result.result(200,"成功",0L,result.getRecords());
    }
}
