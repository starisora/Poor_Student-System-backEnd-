package com.wms.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.springboot.common.QueryPageParam;
import com.wms.springboot.common.Result;
import com.wms.springboot.entity.Goods;
import com.wms.springboot.service.GoodsService;
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
 * @since 2024-03-19
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    //查询
    @GetMapping("/list")
    public Result list(){
        List list = goodsService.lambdaQuery().list();
        //System.out.println(list);
        return Result.result(200,"成功",0L,list);
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        System.out.println(goods);
        try{
            return Result.result(200,"成功",0L,goodsService.save(goods));
        }catch (Exception e){
            return Result.fail();
        }
        //return goodsService.save(Goods)?Result.result(200,"成功",0L,true):Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody Goods goods){
        //return goodsService.updateById(Goods);
        return goodsService.updateById(goods)?Result.success():Result.fail();
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Goods goods){
        return goodsService.saveOrUpdate(goods);
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
//        return goodsService.removeById(id);
        System.out.println(id);
        return goodsService.removeById(id)?Result.success():Result.fail();
    }
    //查询(模糊、匹配)
    @PostMapping("/listP")
    public Result listP(@RequestBody Goods goods){
        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判断是否是空,不是就执行if内容
        if(StringUtils.isNotBlank(goods.getName())){
            //like模糊匹配,eq是匹配
            lambdaQueryWrapper.like(Goods::getName,goods.getName());
        }
        //返回值类型是List<Goods>生效
        //return goodsService.list(lambdaQueryWrapper);
        return Result.result(200,"成功",0L,goodsService.list(lambdaQueryWrapper));
    }

    //分页
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam){
        //其它数据
        HashMap param = queryPageParam.getParam();

        String name = (String) param.get("name");
        String storage = (String) param.get("storage");
        String goodsType = (String) param.get("goodsType");

        Page<Goods> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Goods::getName,(String)param.get("name"));
        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            lambdaQueryWrapper.like(Goods::getStorage,(String)param.get("storage"));
        }
        if (StringUtils.isNotBlank(goodsType) && !"null".equals(goodsType)){
            lambdaQueryWrapper.like(Goods::getGoodsType,(String)param.get("goodsType"));
        }


        IPage result = goodsService.page(page,lambdaQueryWrapper);

        System.out.println("total==="+result.getTotal());
        return Result.result(200,"成功",result.getTotal(),result.getRecords());
        //return Result.result(200,"成功",0L,result.getRecords());
    }

}
