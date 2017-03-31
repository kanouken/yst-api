package org.ost.crm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ost.crm.controller.base.Action;
import org.ost.entity.user.Users;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
@SuppressWarnings("all")
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		boolean flag = false;
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			final String authHeader = request.getHeader("Authorization");
			final String apiKey = request.getHeader("api_key");
			if ((authHeader == null || !authHeader.startsWith("Bearer ")) && apiKey == null) {
				// 无token
				throw new IllegalAccessException();
			}
			// token
			String token = null;
			if (authHeader != null) {
				token = authHeader.substring(7);
			} else {
				token = apiKey;
			}
			Claims body = Jwts.parser().setSigningKey("1stapp").parseClaimsJws(token).getBody();
			String subject = body.getSubject();
			if (subject.equals("1st")) {
				Users currentUser = new Users();
				currentUser.setUserId(Integer.parseInt(body.get("userId").toString()));
				currentUser.setRealname(body.get("realName").toString());
				currentUser.setEmail(body.get("email").toString());
				currentUser.setSchemaId(String.valueOf(body.get("schemaId").toString()));
				currentUser.setDeptId(Integer.parseInt(body.get("deptId").toString()));
				currentUser.setDepartmentName(body.get("deptName") == null?"":body.get("deptName").toString());
				currentUser.setIsDirector(Byte.parseByte(body.get("isDirector").toString()));
				request.setAttribute(Action.LOGIN_USER, currentUser);
			} else {
				throw new IllegalAccessException();
			}
			flag = true;
		}
		return flag;
	}

}
