package com.wms.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.springboot.common.QueryPageParam;
import com.wms.springboot.common.Result;
import com.wms.springboot.entity.Floor;
import com.wms.springboot.service.FloorService;
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
@RequestMapping("/floor")
public class FloorController {
        @Autowired
        private FloorService floorService;

        //查询
        @PostMapping("/list")
        public Result list(){
            List list = floorService.lambdaQuery().list();
            //System.out.println(list);
            return Result.result(200,"成功",0L,list);
        }

        //新增
        @PostMapping("/save")
        public Result save(@RequestBody Floor floor){
            System.out.println(floor);

            return Result.result(200,"成功",0L,floorService.save(floor));

            //return floorService.save(Floor)?Result.result(200,"成功",0L,true):Result.fail();
        }
        //修改
        @PostMapping("/mod")
        public Result mod(@RequestBody Floor floor){
            //return floorService.updateById(Floor);
            return floorService.updateById(floor)?Result.success():Result.fail();
        }
        //新增或修改
        @PostMapping("/saveOrMod")
        public boolean saveOrMod(@RequestBody Floor floor){
            return floorService.saveOrUpdate(floor);
        }
        //删除
        @GetMapping("/del")
        public Result del(@RequestParam Integer id){
//        return floorService.removeById(id);
            System.out.println(id);
            return floorService.removeById(id)?Result.success():Result.fail();
        }
        //查询(模糊、匹配)
        @PostMapping("/listP")
        public Result listP(@RequestBody Floor floor){
            LambdaQueryWrapper<Floor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            //判断是否是空,不是就执行if内容
//        if(StringUtils.isNotBlank(floor.getName())){
//            //like模糊匹配,eq是匹配
//            lambdaQueryWrapper.like(Floor::getName,floor.getName());
//        }
            //返回值类型是List<Floor>生效
            //return floorService.list(lambdaQueryWrapper);
            return Result.result(200,"成功",0L,floorService.list(lambdaQueryWrapper));
        }

        //分页
        @PostMapping("/listPage")
        public Result listPage(@RequestBody QueryPageParam queryPageParam){
            //其它数据
            HashMap param = queryPageParam.getParam();


            Page<Floor> page = new Page<>();
            page.setCurrent(queryPageParam.getPageNum());
            page.setSize(queryPageParam.getPageSize());

            LambdaQueryWrapper<Floor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)){
//            lambdaQueryWrapper.like(Floor::getStorage,(String)param.get("storage"));
//        }
//        if (StringUtils.isNotBlank(floorType) && !"null".equals(floorType)){
//            lambdaQueryWrapper.like(Floor::getFloorType,(String)param.get("floorType"));
//        }


            IPage result = floorService.page(page,lambdaQueryWrapper);

            System.out.println("total==="+result.getTotal());
            return Result.result(200,"成功",result.getTotal(),result.getRecords());
            //return Result.result(200,"成功",0L,result.getRecords());
        }


    }
