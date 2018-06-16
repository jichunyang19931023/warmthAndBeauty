package com.cn.beauty.entity;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class User {

    private Long id;

    private String name;

    @JsonIgnore
    private String password;

    private Integer roleType;

    private Date createTime;
    
    @JsonIgnore
    private Integer deleteFlag;
    
    private String mail;
    
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int i) {
        this.deleteFlag = i;
    }
    
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public User(){
    	super();
    }
    
    public User(String image, String name, String pwd, String mail){
    	this.image = image;
    	this.name = name;
    	this.password = pwd;
    	this.roleType = 1;
    	this.createTime = new Date();
    	this.deleteFlag = 0;
    	this.mail = mail;
    }
}