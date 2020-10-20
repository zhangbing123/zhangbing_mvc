package com.zb.service;

import com.zb.entity.User;
import mvc.annotation.Service;

/**
 * @description:
 * @author: zhangbing
 * @create: 2020-10-20 15:13
 **/
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findUser() {
        System.out.println("查询用户接口调用成功");
        return new User("zhangsan",22);
    }
}
