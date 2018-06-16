package com.cn.beauty.service;

import java.util.List;

import com.cn.beauty.entity.Article;

public interface ArticleService {  
    public Article getArticleById(Long id);

    public List<Article> articleList(String person);
    
	public Long addArticle(String title, String abs, String content, Long userId);

	public Long delArticle(Long id);

	public Long editArticle(Long id, String title, String abs, String content);
} 