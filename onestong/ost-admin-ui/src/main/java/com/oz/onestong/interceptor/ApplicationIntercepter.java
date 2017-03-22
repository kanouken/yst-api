package com.oz.onestong.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截所有请求 在request 对象里放入 application path ${path}
 * @author 夏苗苗
 *
 */
public class ApplicationIntercepter extends  HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		request.setAttribute("basePath", request.getSession().getServletContext().getContextPath());
		//request.setAttribute("basePath", "/1stong");

		return true;
		
	}
	
	

	



}
