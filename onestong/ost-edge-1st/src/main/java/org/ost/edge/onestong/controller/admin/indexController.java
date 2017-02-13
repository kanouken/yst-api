package org.ost.edge.onestong.controller.admin;

import javax.servlet.http.HttpSession;

import org.common.tools.OperateResult;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.services.admin.AdminService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 平台管理员首页
 * 
 * @author mac
 * 
 */
@Controller(value = "adminIndexController")
@RequestMapping("/ost/admin")
public class indexController extends Action {

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/login");

		return mv;
	}

	@RequestMapping(value="logout", method = RequestMethod.GET)
	public String logOut(HttpSession session){
		session.removeAttribute(Constants.SESSION_ADMIN);
		session.invalidate();
		return "redirect:/ost/admin/login";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	public Object method(@RequestParam("loginName") String loginName,
			@RequestParam("password") String password, HttpSession session) {

		OperateResult op = new OperateResult();
		try {
			op = adminService.login(loginName, password);
			if (op.getStatusCode().equals(Constants.HTTP_200)) {

				session.setAttribute(Constants.SESSION_ADMIN, op.getData());
			}

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("管理员登陆 失败  -" + loginName, e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}
		return op;

	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/index");
		return mv;
	}

}
