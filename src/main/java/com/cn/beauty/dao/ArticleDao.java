package com.cn.beauty.dao;

import java.util.List;

import com.cn.beauty.entity.Article;

public interface ArticleDao {

    Long deleteByPrimaryKey(Long id);

    Article selectByPrimaryKey(Long id);

    List<Article> articleList(String person);
    
    Long addArticle(Article article);

	Long editArticle(Article article);
}