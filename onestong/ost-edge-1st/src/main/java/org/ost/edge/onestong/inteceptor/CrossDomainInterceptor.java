package org.ost.edge.onestong.inteceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
@SuppressWarnings("all")
public class CrossDomainInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// String origin = request.getHeader("Origin");
		// if (null != origin) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "false");
		response.addHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,PATCH,OPTIONS");
		response.addHeader("Access-Control-Allow-Headers",
				"origin,x-requested-with,content-type,Authorization,api_key,curPage,perPageSum,version");
		// }
		return true;
	}

}
