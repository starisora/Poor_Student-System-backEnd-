package com.wms.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.springboot.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> listAll();
}
