package com.cn.netdisk.testMyBatis;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.cn.netdisk.entity.User;
import com.cn.netdisk.service.UserService;  
  
@RunWith(SpringJUnit4ClassRunner.class)     
//表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
  
public class TestMyBatis {  
    private static Logger logger = Logger.getLogger(TestMyBatis.class);  
    @Resource  
    private UserService userService = null;  
  
    @Test  
    public void test1() {  
        User user = userService.getUserById(1l);
        logger.info(JSON.toJSONString(user));  
    }  
}