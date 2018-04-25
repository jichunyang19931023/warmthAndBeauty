package com.cn.netdisk.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cn.netdisk.entity.Article;
import com.cn.netdisk.service.ArticleService;
import com.cn.netdisk.util.CommonUtil;
import com.cn.netdisk.util.DateUtil;
import com.cn.netdisk.util.QiniuCloudUtil;
import com.cn.netdisk.util.ResultInfo;

@Controller
@RequestMapping("/article")
public class ArticleController {
	@Resource
	private ArticleService articleService;
	private Logger logger = Logger.getLogger(String.valueOf(ArticleController.class));
	
	@ResponseBody
	@RequestMapping("/list")
	public ResultInfo list(HttpServletRequest request) {
		String person = request.getParameter("person");
		ResultInfo result = new ResultInfo();
		List<Article> list = this.articleService.articleList(person);
		for (Article a : list) {
			String image = CommonUtil.getImgStr(a.getContent());
			if(image != null) {
				a.setImageThumb(image);
			}
			Date createTime = a.getCreateTime();
			a.setCreateTimeStr(DateUtil.date2StringWithTime(createTime));
		}
		result.setCode(200);
		result.setInfo(list);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getArticleById")
	public ResultInfo getArticleById(HttpServletRequest request) {
		String id = request.getParameter("id");
		ResultInfo result = new ResultInfo();
		Article article = this.articleService.getArticleById(Long.parseLong(id));
		Date createTime = article.getCreateTime();
		article.setCreateTimeStr(DateUtil.date2StringWithTime(createTime));
		result.setCode(200);
		result.setInfo(article);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addArticle", method=RequestMethod.POST)
	public ResultInfo add(HttpServletRequest request) {
		ResultInfo result = new ResultInfo();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String abs = content;
		abs = CommonUtil.subStringHTML(content, 80, "...");
		
		logger.info(abs);
		Long userId = Long.parseLong(request.getParameter("userId"));
		Long articleId = this.articleService.addArticle(title, abs, content, userId);
		result.setCode(200);
		result.setInfo(articleId);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/editArticle", method=RequestMethod.POST)
	public ResultInfo editArticle(HttpServletRequest request) {
		ResultInfo result = new ResultInfo();
		Long id = Long.parseLong(request.getParameter("id"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String abs = content;
		abs = CommonUtil.subStringHTML(content, 80, "...");
		System.out.println(content);
		articleService.editArticle(id, title, abs, content);
		result.setCode(200);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delArticle")
	public ResultInfo delArticle(HttpServletRequest request) {
		String id = request.getParameter("id");
		ResultInfo result = new ResultInfo();
		articleService.delArticle(Long.parseLong(id));
		result.setCode(200);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/uploadImg", method=RequestMethod.POST)
	public ResultInfo uploadImg(@RequestParam MultipartFile image, HttpServletRequest request) {
		boolean isAvatar = Boolean.parseBoolean(request.getParameter("isAvatar"));
		ResultInfo result = new ResultInfo();
		if (image.isEmpty()) {
			result.setCode(400);
			result.setMsg("文件为空，请重新上传");
			return result;
		}

		try {
			byte[] bytes = image.getBytes();
			String imageName = UUID.randomUUID().toString();
			
			try {
                //上传到七牛云
                String url = QiniuCloudUtil.put64image(bytes, imageName, isAvatar);
                result.setCode(200);
    			result.setMsg("文件上传成功");
    			result.setInfo(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
		} catch (IOException e) {
			result.setCode(500);
			result.setMsg("文件上传发生异常！");
			return result;
		}
	}
}
