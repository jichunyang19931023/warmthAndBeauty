package com.cn.beauty.util;

/**
 * 返回结果信息类
 * 
 * @author zx
 * 
 * @date 2014-11-18
 */
public class ResultInfo{

	// 返回状态
	private Integer code;
	//状态描述信息
	private String msg;
	//当前页码
	private int page;
	//当页文章数
	private int pageSize;
	//是否有下一页（为提高效率，改为后台判断，此处均返回true)
	private boolean hasNextPage;
	//Info查询总数据量
	private long count;
	//返回数据列表
	private Object info;
	//访问路径
	private String request;
	// 前端回调参数
	private String callback;
	
	public Integer getCode() {
	    return code;
	}
	public void setCode(Integer code) {
	    this.code = code;
	}
	/**
	 * 设置错误码，同时设置错误码提示
	 * ResultInfo.java
	 * @param code
	 * 2014年11月18日
	 */
/*	public void setCodeAndMsg(Integer code) {
	    this.code = code;
	    String msg = GetDescByStatus(code.toString());
	    setMsg(msg);
	}*/

	public String getMsg() {
	    return msg;
	}

	public void setMsg(String msg) {
	    this.msg = msg;
	}

	public int getPage() {
	    return page;
	}

	public void setPage(int page) {
	    this.page = page;
	}

	public int getPageSize() {
	    return pageSize;
	}

	public void setPageSize(int pageSize) {
	    this.pageSize = pageSize;
	}

	public boolean isHasNextPage() {
	    return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
	    this.hasNextPage = hasNextPage;
	}

	public long getCount() {
	    return count;
	}

	public void setCount(long count) {
	    this.count = count;
	}

	public Object getInfo() {
	    return info;
	}

	public void setInfo(Object info) {
	    this.info = info;
	}

	public String getRequest() {
	    return request;
	}
	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
  
	public ResultInfo() {
		super();
	}
   
}
