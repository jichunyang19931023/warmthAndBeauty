package com.cn.netdisk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.netdisk.entity.User;
import com.cn.netdisk.service.UserService;  
  
@Controller  
@RequestMapping("/article")  
public class ArticleController {
    @Resource  
    private UserService userService;  
      
    @RequestMapping("/showUser")
    public String toIndex(HttpServletRequest request,Model model){  
        Long userId = Long.parseLong(request.getParameter("id"));  
        User user = this.userService.getUserById(userId);  
        model.addAttribute("user", user);  
        return "showUser";
    }
}
