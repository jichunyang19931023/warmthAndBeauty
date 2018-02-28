package com.cn.netdisk.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.netdisk.dao.UserDao;
import com.cn.netdisk.entity.User;
import com.cn.netdisk.service.UserService;

@Service("userService")  
public class UserServiceImpl implements UserService {  
    @Resource  
    private UserDao userDao;  
    public User getUserById(Long userId) {  
        return this.userDao.selectByPrimaryKey(userId);  
    }  
  
}  