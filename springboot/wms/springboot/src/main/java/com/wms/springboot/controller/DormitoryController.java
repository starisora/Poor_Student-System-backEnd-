package com.wms.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.springboot.common.QueryPageParam;
import com.wms.springboot.common.Result;
import com.wms.springboot.entity.Dormitory;
import com.wms.springboot.service.DormitoryService;
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
 * @since 2024-03-23
 */
@RestController
@RequestMapping("/dormitory")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;

    //查询
    @GetMapping("/list")
    public Result list(){
        List list = dormitoryService.lambdaQuery().list();
        //System.out.println(list);
        return Result.result(200,"成功",0L,list);
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Dormitory dormitory){
        System.out.println(dormitory);

            return Result.result(200,"成功",0L,dormitoryService.save(dormitory));

        //return dormitoryService.save(Dormitory)?Result.result(200,"成功",0L,true):Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody Dormitory dormitory){
        //return dormitoryService.updateById(Dormitory);
        return dormitoryService.updateById(dormitory)?Result.success():Result.fail();
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Dormitory dormitory){
        return dormitoryService.saveOrUpdate(dormitory);
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
//        return dormitoryService.removeById(id);
        System.out.println(id);
        return dormitoryService.removeById(id)?Result.success():Result.fail();
    }
    //查询(模糊、匹配)
    @PostMapping("/listP")
    public Result listP(@RequestBody Dormitory dormitory){
        LambdaQueryWrapper<Dormitory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判断是否是空,不是就执行if内容
//        if(StringUtils.isNotBlank(dormitory.getName())){
//            //like模糊匹配,eq是匹配
//            lambdaQueryWrapper.like(Dormitory::getName,dormitory.getName());
//        }
        //返回值类型是List<Dormitory>生效
        //return dormitoryService.list(lambdaQueryWrapper);
        return Result.result(200,"成功",0L,dormitoryService.list(lambdaQueryWrapper));
    }

    //分页
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam){
        //其它数据
        HashMap param = queryPageParam.getParam();

        System.out.println(param);

        String floorNum = (String) param.get("floorNum");
        String storage = (String) param.get("storage");
        String dormitoryType = (String) param.get("dormitoryType");

        Page<Dormitory> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<Dormitory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(floorNum) && !"null".equals(floorNum)){
            lambdaQueryWrapper.eq(Dormitory::getFloorNum,(String)param.get("floorNum"));
        }
//        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)){
//            lambdaQueryWrapper.like(Dormitory::getStorage,(String)param.get("storage"));
//        }
//        if (StringUtils.isNotBlank(dormitoryType) && !"null".equals(dormitoryType)){
//            lambdaQueryWrapper.like(Dormitory::getDormitoryType,(String)param.get("dormitoryType"));
//        }


        IPage result = dormitoryService.page(page,lambdaQueryWrapper);

        System.out.println("total==="+result.getTotal());
        return Result.result(200,"成功",result.getTotal(),result.getRecords());
        //return Result.result(200,"成功",0L,result.getRecords());
    }

}
