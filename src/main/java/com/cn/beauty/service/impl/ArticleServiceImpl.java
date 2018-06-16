package com.cn.beauty.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.beauty.dao.ArticleDao;
import com.cn.beauty.entity.Article;
import com.cn.beauty.service.ArticleService;

@Service("articleService")  
public class ArticleServiceImpl implements ArticleService {  
    @Resource  
    private ArticleDao articleDao;  
    
	@Override
	public Article getArticleById(Long id) {
		return articleDao.selectByPrimaryKey(id);
	}
	@Override
	public List<Article> articleList(String person) {
		return articleDao.articleList(person);
	}
	
	@Override
	public Long addArticle(String title, String abs, String content, Long userId) {
		Article article = new Article(title, abs, content, userId);
		return articleDao.addArticle(article);
	}
	@Override
	public Long delArticle(Long id) {
		return articleDao.deleteByPrimaryKey(id);
	}
	@Override
	public Long editArticle(Long id, String title, String abs, String content) {
		Article article = new Article(id, title, abs, content);
		return articleDao.editArticle(article);
	} 
  
}  