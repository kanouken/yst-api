package org.ost.edge.onestong.inteceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.common.tools.db.Page;
import org.ost.edge.onestong.anotations.PageRequired;
import org.ost.edge.onestong.tools.Constants;
import org.ost.edge.onestong.tools.OnezeroHttpServletRequestWrapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
@SuppressWarnings("all")
public class PagedInterceptor extends   HandlerInterceptorAdapter  {

	

	
	
	
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		
		if(arg2.getClass().isAssignableFrom(HandlerMethod.class)){
			
			PageRequired pageRequired = ((HandlerMethod) arg2).getMethodAnnotation(PageRequired.class);
			
           OnezeroHttpServletRequestWrapper  one = new OnezeroHttpServletRequestWrapper(arg0);
           String pageUrl = arg0.getRequestURI();
           //TODO 部署至  外网时 请修改此处
           //pageUrl = pageUrl.replaceFirst("/", "/1stong/");

           
           Map  parameters =  one.getParameterMap();
            if(null != pageRequired){
        		Page page = new Page();
        		if (null == parameters.get("curPage")) {
        			// 第一此访问
        			page.setCurPage(1);
        			page.setPageUrl(pageUrl);
        			//默认每页显示15条 记录 需要更改 请自行在调用的控制器中 改变该参数
        			page.setPerPageSum(Constants.PAGESIZE);

        		} else {

        			page.setCurPage(Integer.valueOf(parameters.get("curPage")
        					.toString().trim()));
        			page.setPerPageSum(Constants.PAGESIZE);
	
        			page.setPageUrl(pageUrl);

        		}
        		
        		
        		arg0.setAttribute("page", page);
            	
            }
              
		}
     
		return true;
	}

	



}
