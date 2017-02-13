package org.ost.edge.onestong.controller.web.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.edge.onestong.anotations.AuthTarget;
import org.ost.edge.onestong.anotations.PageRequired;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.account.Account;
import org.ost.edge.onestong.model.authority.Role;
import org.ost.edge.onestong.model.department.Department;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.admin.domain.DomainOrderService;
import org.ost.edge.onestong.services.authority.AuthorityService;
import org.ost.edge.onestong.services.authority.RoleService;
import org.ost.edge.onestong.services.web.department.DepartmentService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 增加 删除功能
 * 
 * @author xnq
 * 
 */
@Controller
@SuppressWarnings("all")
@RequestMapping("/user")
public class UserController extends Action {
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UsersService usersService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private DomainOrderService domainOrderService;

	@Autowired
	private AuthorityService authorityService;

	/**
	 * 分页处理 加入 域id 权限检查
	 * 
	 * @return
	 */
	@AuthTarget(value = { "userListOwn", "userListAll" })
	@SuppressWarnings("all")
	@PageRequired
	@RequestMapping("list")
	public ModelAndView userList(HttpServletRequest request, User user,
			HttpSession session) {
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		user.setDomainId(sessionUser.getDomainId());
		Page page = (Page) request.getAttribute(PAGE);
		Map<String, Object> pageAttr = new HashMap<String, Object>();
		pageAttr.put("realname", user.getRealname());
		page.setPageAttrs(pageAttr);
		ModelAndView mv = new ModelAndView("user/user_list");
		user.setDeptId(sessionUser.getDeptId());
		int totalRecords = usersService.findAllUsersCount(user,
				(List<Map<String, Object>>) sessionLocal
						.get(Constants.SESSION_PERM_TIP));
		page.setTotalRecords(totalRecords);
		page.setPageCount(getPageCount(page));
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		mv.addObject("users", usersService.findAllUsers(user, rb,
				(List<Map<String, Object>>) sessionLocal
						.get(Constants.SESSION_PERM_TIP)));
		return mv;

	}

	@AuthTarget(value = { "addUser" })
	@RequestMapping("preUserAdd")
	public ModelAndView preAddUser(HttpSession session) {

		// roles
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		Department dept = new Department();
		dept.setDomainId(sessionUser.getDomainId());
		List<Department> depts = this.departmentService
				.findAllDepartments(dept);
		Role role = (Role) sessionLocal.get("role");
		if (null == role) {
			role = new Role();
			role.setLevel(Byte.valueOf("0"));
			role.setDomainId(sessionUser.getDomainId());
		}
		// TODO 判断 是不是 企业账户 role 等于 null
		List<Role> roles = this.roleService.findRolesBlow(role);
		ModelAndView mv = new ModelAndView("user/user_add");
		mv.addObject("depts", depts);
		mv.addObject("roles", roles);

		return mv;

	}

	@AuthTarget(value = { "updateUser" })
	@RequestMapping(value = "update/{userId}", method = RequestMethod.GET)
	public ModelAndView preUpdateUser(HttpSession session,
			@PathVariable("userId") Integer userId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/user_update");

		User user = this.usersService.findOneById(userId);
		if (null == user) {

			mv.setViewName("common/404");

		} else {

			// roles
			Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
					session, Constants.SESSION_USER);

			User sessionUser = (User) sessionLocal.get("user");
			Department dept = new Department();
			dept.setDomainId(sessionUser.getDomainId());

			List<Department> depts = this.departmentService
					.findAllDepartments(dept);

			Role role = (Role) sessionLocal.get("role");
			if (null == role) {
				role = new Role();
				role.setLevel(Byte.valueOf("0"));
				role.setDomainId(sessionUser.getDomainId());

			}
			// TODO 判断 是不是 企业账户 role 等于 null
			List<Role> roles = this.roleService.findRolesBlow(role);

			role = this.authorityService.findRoleByUser(user);
			mv.addObject("user", user);
			mv.addObject("roles", roles);
			mv.addObject("depts", depts);
			mv.addObject("userRole", role);
		}

		return mv;

	}

	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	public Object addUser(User user, Account account, Role role,
			HttpSession session) {
		// ModelAndView mv = new ModelAndView("user/user_add");
		boolean flag = false;
		OperateResult op = new OperateResult();
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		if (StringUtils.isBlank(user.getEmail()) || null == user.getDeptId()
				|| null == user.getDomainId()
				|| StringUtils.isBlank(account.getLoginPassword())
				|| null == role.getRoleId()
				|| StringUtils.isBlank(account.getOptional1())) {

			op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
			op.setDescription("参数填写不完整！");
			op.setData(null);

			return op;
		} else if (!account.getOptional1().equals(account.getLoginPassword())) {
			op.setStatusCode(Constants.USER_CONFIRM_IN_CORRECT);
			op.setDescription("两次填写的密码不一致！");
			op.setData(null);
			return op;
		}

		else {
			// 检查 激活码是否可用

			if (!this.domainOrderService.isValidActiveCodeNumValid(user
					.getDomainId())) {
				op.setStatusCode(Constants.ACTIVE_CODE_INVALID);
				op.setDescription("您的激活码不够用了！");
				op.setData(null);
				return op;
			}
			try {
				user.setCreator(sessionUser.getRealname());
				user.setUpdator(sessionUser.getRealname());
				account.setCreator(sessionUser.getRealname());
				account.setUpdator(sessionUser.getRealname());
				user.setOptional1(null);
				flag = true;
				usersService.addUser(user, account, role);
				if (flag) {

					op.setStatusCode(Constants.HTTP_200);
					// 显示该提示信息后 页面跳转到 用户列表页面
					op.setDescription("用户添加成功！");
					return op;
				}
			} catch (Exception e) {

				if (logger.isErrorEnabled()) {
					logger.error("用户添加失败", e);
				}
				op.setStatusCode(Constants.SERVER_ERROR);
				op.setDescription("服务器异常！稍后再试");
				op.setData(null);
			}

		}

		return op;

	}

	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "deleteUser", method = RequestMethod.POST)
	public Object deleteUser(User user, HttpSession session) {
		OperateResult op = new OperateResult();
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		if (null == user.getUserId()) {

			op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
			op.setDescription("缺省用户id");
			op.setData(null);
			return op;
		} else {
			try {
				this.usersService.deleteUser(user);
				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("成功删除用户！");
				return op;

			} catch (Exception e) {

				if (logger.isErrorEnabled()) {
					logger.error("用户删除失败！", e);
				}
				op.setStatusCode(Constants.SERVER_ERROR);
				op.setDescription("服务器异常！稍后再试");
				op.setData(null);
			}

		}

		return op;

	}

	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	public Object updateUser(User user, Account account, Role role,
			HttpSession session) {
		// ModelAndView mv = new ModelAndView("user/user_add");
		boolean flag = false;
		OperateResult op = new OperateResult();
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		if (StringUtils.isBlank(user.getEmail()) || null == user.getDeptId()
				|| StringUtils.isBlank(user.getRealname())
				|| StringUtils.isBlank(user.getPhoneNum())
				|| null == role.getRoleId()) {
			op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
			op.setDescription("参数填写不完整！");
			op.setData(null);

			return op;
		} else if ((StringUtils.isNotBlank(account.getLoginPassword()))
				&& !account.getOptional1().equals(account.getLoginPassword())) {
			op.setStatusCode(Constants.USER_CONFIRM_IN_CORRECT);
			op.setDescription("两次填写的密码不一致！");
			op.setData(null);
			return op;
		}

		else {
			try {
				user.setUpdator(sessionUser.getRealname());
				user.setUpdateTime(new Date());
				account.setUpdator(sessionUser.getRealname());
				account.setUpdateTime(new Date());
				user.setOptional1(null);
				usersService.updateUser(user, account, role);

				op.setStatusCode(Constants.HTTP_200);
				// 显示该提示信息后 页面跳转到 用户列表页面
				op.setDescription("用户修改成功！");
				return op;

			} catch (Exception e) {

				if (logger.isErrorEnabled()) {
					logger.error("用户修改失败", e);
				}
				op.setStatusCode(Constants.SERVER_ERROR);
				op.setDescription("服务器异常！稍后再试");
				op.setData(null);
			}

		}

		return op;

	}

	@ResponseBody
	@RequestMapping(value = "unBindUser", method = RequestMethod.POST)
	public Object unBindUser(HttpSession session, User user) {
		OperateResult op = new OperateResult();
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		try {
			user = this.usersService.findOneById(user.getUserId());
			if (null == user) {

				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("不存在的用户");
				op.setData(null);
				return op;
			}

			user.setUpdateTime(new Date());
			user.setUpdator(sessionUser.getRealname());
			user.setDeviceId(null);
			this.usersService.unBindUser(user);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("成功解绑！");
			op.setData(null);

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("用户解绑失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;
	}

}
