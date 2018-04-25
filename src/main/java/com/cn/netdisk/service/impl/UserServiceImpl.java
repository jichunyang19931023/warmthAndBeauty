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
	@Override
	public Long addUser(String image, String username, String password, String mail) {
		User user = new User(image, username, password, mail);
		return this.userDao.addUser(user);
	}
	@Override
	public int checkUsername(String username) {
		return this.userDao.checkUsername(username);
	}
	@Override
	public User loginCheck(String username, String pwdMd5) {
		return this.userDao.loginCheck(username, pwdMd5);
	}
}  