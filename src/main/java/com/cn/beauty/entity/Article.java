package com.cn.beauty.entity;

import java.util.Date;

import javax.persistence.Transient;

public class Article {

    private Long id;

    private String title;
    
    private String abs;

	private Date createTime;
	
	@Transient
	private String createTimeStr;
	
	@Transient
	private String imageThumb;

	private Date updateTime;

    private Long userId;

    private Integer deleteFlag = 0;

    private String content;
    
    private User user;

    public Article(){
    	super();
    }
    
    public Article(Long id, String title, String abs, String content){
    	this.setId(id);
    	this.setUpdateTime(new Date());
    	this.setTitle(title);
		this.setAbs(abs);
		this.setContent(content);
	}
    
    public Article(String title, String abs, String content, Long userId){
		this.setTitle(title);
		this.setAbs(abs);
		this.setContent(content);
		this.setUserId(userId);
		this.setCreateTime(new Date());
	}
	
    public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}
	
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImageThumb() {
		return imageThumb;
	}

	public void setImageThumb(String imageThumb) {
		this.imageThumb = imageThumb;
	}
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}