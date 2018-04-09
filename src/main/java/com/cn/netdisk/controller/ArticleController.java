package com.cn.netdisk.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.netdisk.entity.Article;
import com.cn.netdisk.service.ArticleService;
import com.cn.netdisk.util.DateUtil;
import com.cn.netdisk.util.ResultInfo;  
  
@Controller  
@RequestMapping("/article")  
public class ArticleController {
    @Resource  
    private ArticleService articleService;
      
    @ResponseBody
    @RequestMapping("/list")
    public ResultInfo list(HttpServletRequest request){
    	ResultInfo result = new ResultInfo();
        List<Article> list = this.articleService.articleList();
        for(Article a : list){
        	Date createTime = a.getCreateTime();
        	a.setCreateTimeStr(DateUtil.date2StringWithTime(createTime));
        }
        result.setCode(200);
        result.setInfo(list);
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/addArticle")
    public ResultInfo add(HttpServletRequest request){
    	ResultInfo result = new ResultInfo();
    	String title = request.getParameter("title");
    	String content = request.getParameter("content");
    	Long userId = Long.parseLong(request.getParameter("userId"));
    	Date createTime = new Date();
        Long articleId = this.articleService.addArticle(title, content, createTime, userId);
        result.setCode(200);
        result.setInfo(articleId);
        return result;
    }
}
