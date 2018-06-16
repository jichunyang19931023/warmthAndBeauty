package com.cn.beauty.util;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class loginFilter implements Filter {
	private String[] excludedPageArray;
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludedPages = filterConfig.getInitParameter("excludedPages");    
		if (excludedPages != null) {    
			excludedPageArray = excludedPages.split(",");    
		} 
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		request.setCharacterEncoding("utf-8");
		boolean isExcludedPage = false;     
		for (String page : excludedPageArray) {// 判断是否在过滤url之外
			String url = ((HttpServletRequest) request).getServletPath();
			if (page.equals(url) || url.contains(page)) {
				isExcludedPage = true;
				break;
			}
		}
		if(isExcludedPage){
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		RedisTemplate redisTemplate;
        WebApplicationContext ac = WebApplicationContextUtils
                .getWebApplicationContext(request.getServletContext());
        redisTemplate = (RedisTemplate) ac.getBean("redisTemplate");
		String cookieValue = "";
		Cookie cs[] = request.getCookies();
		for (int i = 0; cs != null && i < cs.length; i++) {
			Cookie c = cs[i];
			if ("userInfo".equals(c.getName())) {
				cookieValue = c.getValue();
				cookieValue = URLDecoder.decode(cookieValue,"utf-8");
				break;
			}
		}
		String redisUserInfo = (String) redisTemplate.opsForValue().get(cookieValue);
		if(redisUserInfo != null){
        	redisTemplate.opsForValue().set(cookieValue, redisUserInfo, 60*60*30);
        	filterChain.doFilter(servletRequest, servletResponse);
        	return;
        }
		
	}

	public void destroy() {

	}
}