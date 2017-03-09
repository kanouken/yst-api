package ost.file.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
