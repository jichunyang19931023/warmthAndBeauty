package com.cn.beauty.dao;

import org.apache.ibatis.annotations.Param;

import com.cn.beauty.entity.User;

public interface UserDao {

    int deleteByPrimaryKey(Long id);
    //用户注册
    Long addUser(User user);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    //检测用户名重复性
	int checkUsername(String username);
	
	User loginCheck(@Param("username") String username,@Param("pwdMd5") String pwdMd5);
}