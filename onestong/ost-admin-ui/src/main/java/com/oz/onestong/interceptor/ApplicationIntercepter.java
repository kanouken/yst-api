package com.oz.onestong.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import sun.tools.tree.ThisExpression;

/**
 * 拦截所有请求 在request 对象里放入 application path ${path}
 * @author 夏苗苗
 *
 */
public class ApplicationIntercepter extends  HandlerInterceptorAdapter{
	private String pathStrip;
	
	public void setPathStrip(String pathStrip){
		this.pathStrip = pathStrip;
	}
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		request.setAttribute("basePath", request.getSession().getServletContext().getContextPath());
		request.setAttribute("basePath", pathStrip);
		return true;
		
	}
	
	

	



}
