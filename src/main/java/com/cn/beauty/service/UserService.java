package com.cn.beauty.service;

import com.cn.beauty.entity.User;

public interface UserService {  
    public User getUserById(Long userId);

	public Long addUser(String image, String username, String password, String mail);

	public int checkUsername(String username);

	public User loginCheck(String username, String pwdMd5);
} 