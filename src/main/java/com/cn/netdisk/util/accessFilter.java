package com.cn.netdisk.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class accessFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String origin = servletRequest.getRemoteHost() + ":" + servletRequest.getRemotePort();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Authentication");
		/*
		 * response.setHeader("Access-Control-Allow-Methods",
		 * "POST,GET,OPTIONS,DELETE");
		 * response.setHeader("Access-Control-Max-Age","3600");
		 * response.setHeader("Access-Control-Allow-Credentials","true");
		 */
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {

	}
}