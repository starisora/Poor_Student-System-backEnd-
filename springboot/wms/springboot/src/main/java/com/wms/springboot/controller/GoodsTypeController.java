package com.wms.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.springboot.common.QueryPageParam;
import com.wms.springboot.common.Result;
import com.wms.springboot.entity.GoodsType;
import com.wms.springboot.service.GoodsTypeService;
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
 * @since 2024-03-18
 */
@RestController
@RequestMapping("/goodsType")
public class GoodsTypeController {

    @Autowired
    private GoodsTypeService goodsTypeService;

    //查询
    @GetMapping("/list")
    public Result list(){
        List list = goodsTypeService.lambdaQuery().list();
        //System.out.println(list);
        return Result.result(200,"成功",0L,list);
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody GoodsType goodsType){
        System.out.println(goodsType);
            return Result.result(200,"成功",0L,goodsTypeService.save(goodsType));

        //return goodsTypeService.save(goodsType)?Result.result(200,"成功",0L,true):Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody GoodsType goodsType){
        //return goodsTypeService.updateById(goodsType);
        return goodsTypeService.updateById(goodsType)?Result.success():Result.fail();
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody GoodsType goodsType){
        return goodsTypeService.saveOrUpdate(goodsType);
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
//        return goodsTypeService.removeById(id);
        System.out.println(id);
        return goodsTypeService.removeById(id)?Result.success():Result.fail();
    }
    //查询(模糊、匹配)
    @PostMapping("/listP")
    public Result listP(@RequestBody GoodsType goodsType){
        LambdaQueryWrapper<GoodsType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判断是否是空,不是就执行if内容
        if(StringUtils.isNotBlank(goodsType.getName())){
            //like模糊匹配,eq是匹配
            lambdaQueryWrapper.like(GoodsType::getName,goodsType.getName());
        }
        //返回值类型是List<goodsType>生效
        //return goodsTypeService.list(lambdaQueryWrapper);
        return Result.result(200,"成功",0L,goodsTypeService.list(lambdaQueryWrapper));
    }

    //分页
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam){
        //其它数据
        HashMap param = queryPageParam.getParam();

        String name = (String) param.get("name");

        Page<GoodsType> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<GoodsType> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(GoodsType::getName,(String)param.get("name"));
        }

        IPage result = goodsTypeService.page(page,lambdaQueryWrapper);

        System.out.println("total==="+result.getTotal());
        return Result.result(200,"成功",result.getTotal(),result.getRecords());
        //return Result.result(200,"成功",0L,result.getRecords());
    }

}
