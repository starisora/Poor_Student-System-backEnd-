package com.wms.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.springboot.common.QueryPageParam;
import com.wms.springboot.common.Result;
import com.wms.springboot.common.ReturnData;
import com.wms.springboot.entity.RepairReport;
import com.wms.springboot.entity.User;
import com.wms.springboot.service.RepairReportService;
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
@RequestMapping("/repairReport")
public class RepairReportController {
    @Autowired
    private RepairReportService repairReportService;

    //查询
    @PostMapping("/list")
    public Result list(){
        List list = repairReportService.lambdaQuery().list();
        //System.out.println(list);
        return Result.result(200,"成功",0L,list);
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody RepairReport repairReport){
        System.out.println(repairReport);
        try{
            return Result.result(200,"成功",0L,repairReportService.save(repairReport));
        }catch (Exception e){
            return Result.fail();
        }
        //return repairReportService.save(RepairReport)?Result.result(200,"成功",0L,true):Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody RepairReport repairReport){
        //return repairReportService.updateById(RepairReport);
        return repairReportService.updateById(repairReport)?Result.success():Result.fail();
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody RepairReport repairReport){
        return repairReportService.saveOrUpdate(repairReport);
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
//        return repairReportService.removeById(id);
        System.out.println(id);
        return repairReportService.removeById(id)?Result.success():Result.fail();
    }
    //查询(模糊、匹配)
    @PostMapping("/listP")
    public Result listP(@RequestBody RepairReport repairReport){
        LambdaQueryWrapper<RepairReport> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        //判断是否是空,不是就执行if内容
//        if(StringUtils.isNotBlank(repairReport.getName())){
//            //like模糊匹配,eq是匹配
//            lambdaQueryWrapper.like(RepairReport::getName,repairReport.getName());
//        }
        //返回值类型是List<RepairReport>生效
        //return repairReportService.list(lambdaQueryWrapper);
        return Result.result(200,"成功",0L,repairReportService.list(lambdaQueryWrapper));
    }

    //分页
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam){
        //其它数据
        HashMap param = queryPageParam.getParam();
        System.out.println(param);

        String situationType = (String) param.get("situationType");
        String userName = (String) param.get("userName");
        String status = (String) param.get("status");

        Page<RepairReport> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<RepairReport> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(situationType) && !"null".equals(situationType)){
            lambdaQueryWrapper.eq(RepairReport::getSituationType,(String)param.get("situationType"));
        }
        if (StringUtils.isNotBlank(userName) && !"null".equals(userName)){
            lambdaQueryWrapper.eq(RepairReport::getUserName,(String)param.get("userName"));
        }
        if (StringUtils.isNotBlank(status) && !"null".equals(status)){
            lambdaQueryWrapper.eq(RepairReport::getStatus,(String)param.get("status"));
        }


        IPage result = repairReportService.page(page,lambdaQueryWrapper);

        System.out.println("total==="+result.getTotal());
        return Result.result(200,"成功",result.getTotal(),result.getRecords());
        //return Result.result(200,"成功",0L,result.getRecords());
    }

    @PostMapping("/editStatus")
    public Result addDorNum(@RequestBody ReturnData returnData){
        System.out.println("执行add函数");
        System.out.println(returnData);
//        System.out.println(returnData);
        HashMap param = returnData.getParam();

        String id = param.get("id").toString();
        String status = param.get("status").toString();

        LambdaUpdateWrapper<RepairReport> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RepairReport::getId,id).set(RepairReport::getStatus, status);
        // 或者使用 updateWrapper.set(User::getFieldName, "");

        boolean affectedRows = repairReportService.update(null, updateWrapper);
        // 处理更新结果
        return affectedRows?Result.success():Result.fail();
    }
}
