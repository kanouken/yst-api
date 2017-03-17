package com.oz.onestong.interceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import com.oz.onestong.anotations.AuthTarget;
import com.oz.onestong.anotations.PageRequired;
import com.oz.onestong.controller.base.Action;
import com.oz.onestong.model.Page;
import com.oz.onestong.tools.Constants;
import com.oz.onestong.tools.OnezeroHttpServletRequestWrapper;

@SuppressWarnings("all")
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {

	/**
	 * {moudles=[{moudleId=1, moudleName=用户管理, moudleIco=am-icon-user,
	 * moudleUri=/user/list, moudleTag=ost_moudle_users}, {moudleId=2,
	 * moudleName=部门管理, moudleIco=am-icon-group, moudleUri=/department/list,
	 * moudleTag=ost_moudle_depts}, {moudleId=3, moudleName=角色管理,
	 * moudleIco=am-icon-wrench, moudleUri=/role/list,
	 * moudleTag=ost_moudle_roles}], perms=[{permName=添加用户, pmId=1,
	 * methodName=addUser}, {permName=更新用户, pmId=1, methodName=updateUser},
	 * {permName=禁用用户, pmId=1, methodName=deleteUser}, {permName=解绑用户设备, pmId=1,
	 * methodName=unBindUser}, {permName=查看用户(本部门成员), pmId=1,
	 * methodName=userListOwn}, {permName=添加部门, pmId=2,
	 * methodName=addDepartment}, {permName=删除部门, pmId=2,
	 * methodName=deleteDepartment}, {permName=查看部门, pmId=2,
	 * methodName=departmentList}, {permName=更新部门, pmId=2,
	 * methodName=updateDepartment}, {permName=添加角色, pmId=3,
	 * methodName=addRole}, {permName=更新角色, pmId=3, methodName=updateRole},
	 * {permName=查看角色列表, pmId=3, methodName=roleList}, {permName=删除角色, pmId=3,
	 * methodName=deleteRole}, {permName=接收来自所有其他成员的事件推送, pmId=4,
	 * methodName=all_events}, {permName=创建考勤事件, pmId=4,
	 * methodName=create_attence}, {permName=创建外访事件, pmId=4,
	 * methodName=create_visit}, {permName=查看部门内其他成员的事件简报, pmId=5,
	 * methodName=self_below_reports}],
	 * domain=com.oz.onestong.model.domain.Domain@60b78948,
	 * account=com.oz.onestong.model.account.Account@31960793,
	 * role=com.oz.onestong.model.authority.Role@70cb5d53,
	 * user=com.oz.onestong.model.user.User@375c2dff}
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		boolean flag = false;
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			Map<String, Object> sessionLocal =  (Map<String, Object>) session.getAttribute(Constants.SESSION_USER);
			List<Map<String, Object>> perms = (List<Map<String, Object>>) sessionLocal.get(Constants.SESSION_PERM_TIP);
			HandlerMethod handle = (HandlerMethod) handler;
			
			AuthTarget  authTarget =  handle.getMethodAnnotation(AuthTarget.class);
			// 当前处理 请求的 方法名称
			if(null == perms){
				
				return true;
			}
			//处理 特别的情况   preAddUser  ----->> addUser
			//如果访问  的方法 是 preAddUser , get("preAddUser ") ---------> addUser
			
			String methodName = handle.getMethod().getName();
			String avalidMehtodName = "";
			for (Map<String, Object> tmp : perms) {
				avalidMehtodName = tmp.get("methodName").toString();
				if (methodName.equals(avalidMehtodName)) {

					flag = true;
					break;
				}

			}
			if(null !=authTarget ){
				String[] others =  authTarget.value();
				for(String tmp : others){
					for (Map<String, Object> _tmp : perms) {
						avalidMehtodName = _tmp.get("methodName").toString();
						if (tmp.equals(avalidMehtodName)) {

							flag = true;
							break;
						}

					}
					
				}
				
			}
			
			
		

		}
		return flag;
	}

}
