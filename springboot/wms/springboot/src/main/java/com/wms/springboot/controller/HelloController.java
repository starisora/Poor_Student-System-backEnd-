package com.wms.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.springboot.common.BeanCopyUtils;
import com.wms.springboot.common.QueryPageParam;
import com.wms.springboot.common.Result;
import com.wms.springboot.common.ReturnData;
import com.wms.springboot.entity.Menu;
import com.wms.springboot.entity.User;
import com.wms.springboot.entity.vo.UserVo;
import com.wms.springboot.service.UserService;
import com.wms.springboot.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class HelloController {

    @GetMapping
    public String hello(){
        return "hello spingboot!!!";
    }

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/findByNum")
    public Result findByNum(@RequestParam String num){
        List list = userService.lambdaQuery().eq(User::getNum,num).list();
        //System.out.println(list);
        return list.size()>0?Result.result(200,"成功",0L,list):Result.fail();
    }

    @GetMapping("/list")
    public List<User> list(){
        //return userService.list();
        //下行代码和上行代码功能相同，只是学习使用
        return userService.listAll();
    }

    @PostMapping("/list")
    public Result listVo(){
        //return userService.list();
        //下行代码和上行代码功能相同，只是学习使用
        List<User> users = userService.listAll();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(users, UserVo.class);
//        List<UserVo> userVos = BeanCopyUtils.copyBeanList(userPage.getRecords(), UserVo.class);
        return Objects.nonNull(userVos)?Result.sucData(userVos):Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        System.out.println(user);
        try{
            return Result.result(200,"成功",0L,userService.save(user));
        }catch (Exception e){
            return Result.fail();
        }
        //return userService.save(user)?Result.result(200,"成功",0L,true):Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody User user){
        //return userService.updateById(user);
        return userService.updateById(user)?Result.success():Result.fail();
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
//        return userService.removeById(id);
        System.out.println(id);
        return userService.removeById(id)?Result.success():Result.fail();
    }
    //查询(模糊、匹配)
    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判断是否是空,不是就执行if内容
        if(StringUtils.isNotBlank(user.getName())){
            //like模糊匹配,eq是匹配
            lambdaQueryWrapper.like(User::getName,user.getName());
        }
        //返回值类型是List<User>生效
        //return userService.list(lambdaQueryWrapper);
        return Result.result(200,"成功",0L,userService.list(lambdaQueryWrapper));
    }

    //分页
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam queryPageParam){
        System.out.println(queryPageParam);
        System.out.println("num==="+queryPageParam.getPageNum());
        System.out.println("size==="+queryPageParam.getPageSize());

        //其它数据
        HashMap param = queryPageParam.getParam();
        System.out.println("name==="+(String) param.get("name"));//没用过
        System.out.println("no==="+(String) param.get("no"));//没用过

        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        String roleId = (String) param.get("roleId");
        String dormitoryNum = (String) param.get("dormitoryNum");

        //下列三行功能相同
        //Page<User> page = new Page<>(1,10);
        //page.setCurrent(1);
        //page.setSize(10);

        Page<User> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,(String)param.get("name"));
        }

        if (StringUtils.isNotBlank(sex) && !"null".equals(sex)){
            lambdaQueryWrapper.eq(User::getSex,(String)param.get("sex"));
        }

        if (StringUtils.isNotBlank(roleId) && !"null".equals(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId,(String)param.get("roleId"));
        }

        if (StringUtils.isNotBlank(dormitoryNum) && !"null".equals(dormitoryNum)){
            lambdaQueryWrapper.eq(User::getDormitoryNum,(String)param.get("dormitoryNum"));
        }


        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println("total==="+result.getTotal());
        return Result.result(200,"成功",result.getTotal(),result.getRecords());
        //return Result.result(200,"成功",0L,result.getRecords());
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        //return userService.updateById(user);
        //return userService.updateById(user)?Result.success():Result.fail();
        System.out.println(user);
        //验证账号密码
        List list = userService.lambdaQuery()
                .eq(User::getNum,user.getNum())
                .eq(User::getPassword,user.getPassword()).list();
        System.out.println(list);

        if(list.size()>0){
            //创建哈希,存储两个json数据
            HashMap res = new HashMap();

            //获取用户数据,验证权限数据,渲染出对应表格
            User user1 = (User) list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright,user1.getRoleId()).list();

            res.put("user",user1);
            res.put("menu",menuList);
            return Result.sucData(res);
        }
        return Result.fail();
    }
    @PostMapping("/numIsNull")
    public Result numIsNull(@RequestBody QueryPageParam queryPageParam){
//        System.out.println("执行null函数");
        System.out.println(queryPageParam);
        //其它数据
        HashMap param = queryPageParam.getParam();

        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        String roleId = (String) param.get("roleId");
        //下列三行功能相同
        //Page<User> page = new Page<>(1,10);
        //page.setCurrent(1);
        //page.setSize(10);

        Page<User> page = new Page<>();
        page.setCurrent(queryPageParam.getPageNum());
        page.setSize(queryPageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.isNull(User::getDormitoryNum);
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,(String)param.get("name"));
        }

        if (StringUtils.isNotBlank(sex) && !"null".equals(sex)){
            lambdaQueryWrapper.eq(User::getSex,(String)param.get("sex"));
        }

        if (StringUtils.isNotBlank(roleId) && !"null".equals(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId,(String)param.get("roleId"));
        }


        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println("total==="+result.getTotal());
        return Result.result(200,"成功",result.getTotal(),result.getRecords());
        //return Result.result(200,"成功",0L,result.getRecords());
    }

    @GetMapping("/clearDorNum")
    public Result clearDorNum(@RequestParam Integer id){
//        System.out.println("执行null函数");
        System.out.println(id);
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId,id).set(User::getDormitoryNum, null);
        // 或者使用 updateWrapper.set(User::getFieldName, "");

        boolean affectedRows = userService.update(null, updateWrapper);
        // 处理更新结果
        return affectedRows?Result.success():Result.fail();
    }

    @PostMapping("/addDorNum")
    public Result addDorNum(@RequestBody ReturnData returnData){
        System.out.println("执行add函数");
        System.out.println(returnData);
//        System.out.println(returnData);
        HashMap param = returnData.getParam();

        String id = param.get("id").toString();
        String dormitoryNum = param.get("dormitoryNum").toString();

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId,id).set(User::getDormitoryNum, dormitoryNum);
        // 或者使用 updateWrapper.set(User::getFieldName, "");

        boolean affectedRows = userService.update(null, updateWrapper);
        // 处理更新结果
        return affectedRows?Result.success():Result.fail();
    }
}
