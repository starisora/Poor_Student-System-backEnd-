package com.wms.springboot.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wms.springboot.entity.User;
import lombok.Data;

@Data
public class UserVo{
    private int id;
    private String num;
    private String name;
    private int age;
    private int sex;
    private String phone;

    //roleId对应数据库中的role_id
    private int roleId;

    //对应数据库字段
    @TableField("is_valid")
    private String isValid;

    private String dormitoryNum;
}
