package com.zb.controller;

import com.zb.entity.User;
import com.zb.service.UserService;
import mvc.annotation.Autowired;
import mvc.annotation.Controller;
import mvc.annotation.RequestMapping;

/**
 * @description: 用户控制器
 * @author: zhangbing
 * @create: 2020-10-20 10:24
 **/
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //定义方法
    @RequestMapping("/findUser")
    public User findUser() {
        //调用服务层
        return userService.findUser();
    }
}
