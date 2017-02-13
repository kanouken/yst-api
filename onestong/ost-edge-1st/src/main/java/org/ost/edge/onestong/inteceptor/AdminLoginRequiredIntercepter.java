package org.ost.edge.onestong.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ost.edge.onestong.services.web.account.AccountService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 过滤所有需要登陆的请求
 * @author 夏苗苗
 *
 */
public class AdminLoginRequiredIntercepter extends  HandlerInterceptorAdapter{

	@Autowired
	private AccountService accountService;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		Object session_user = request.getSession().getAttribute(Constants.SESSION_ADMIN);
		
		if(null!= session_user){
			
			return true;
		}else{
			
			response.sendRedirect(request.getSession().getServletContext().getContextPath()+"/ost/admin/login");
			return false;
		}
		
		
		
		
	}
	
	

	



}
