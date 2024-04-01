package com.wms.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {
    private int id;
    private String num;
    private String name;
    private String password;
    private int age;
    private int sex;
    private String phone;

    //roleId对应数据库中的role_id
    private int roleId;

    //对应数据库字段
    @TableField("is_valid")
    private String isValid;
    @TableField("dormitory_num")
    private String dormitoryNum;
}
