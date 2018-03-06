package com.cn.netdisk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cn.netdisk.entity.User;
import com.cn.netdisk.service.UserService;
import com.cn.netdisk.util.ResultInfo;  
  
@Controller  
@RequestMapping("/user")  
public class UserController {  
    @Resource  
    private UserService userService;  
    
    @ResponseBody
    @RequestMapping("/addUser")
    public ResultInfo addUser(HttpServletRequest request){
    	ResultInfo result = new ResultInfo();
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String mail = request.getParameter("mail");
    	Long userId = this.userService.addUser(username,password,mail);
    	User user = this.userService.getUserById(userId);
    	result.setCode(200);
    	result.setInfo(user);
    	return result;
    }

    @ResponseBody
    @RequestMapping("/checkUsername")
    public ResultInfo checkUsername(HttpServletRequest request){
    	ResultInfo result = new ResultInfo();
    	String username = request.getParameter("username");
    	int count = this.userService.checkUsername(username);
    	JSONObject repeat = new JSONObject();
    	repeat.put("repeat", count > 0? true : false);
    	result.setCode(200);
    	result.setInfo(repeat);
    	return result;
    }
    
    @RequestMapping("/showUser")
    public String toIndex(HttpServletRequest request,Model model){  
        Long userId = Long.parseLong(request.getParameter("id"));  
        User user = this.userService.getUserById(userId);  
        model.addAttribute("user", user);
        return "showUser";
    }
}
