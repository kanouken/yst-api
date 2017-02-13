package org.ost.edge.onestong.controller.web.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.edge.onestong.anotations.AuthTarget;
import org.ost.edge.onestong.anotations.PageRequired;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.controller.base.Action.OST_MOUDLES;
import org.ost.edge.onestong.model.authority.Role;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.authority.AuthorityService;
import org.ost.edge.onestong.services.authority.RoleService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings("all")
@Controller
@RequestMapping("/role")
public class RoleController extends Action {
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;

	/**
	 * 分页处理 加入 域id
	 * 
	 * @return
	 */
	@SuppressWarnings("all")
	@PageRequired
	@RequestMapping("list")
	public ModelAndView roleList(HttpServletRequest request, Role role,
			HttpSession session) {
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		role.setDomainId(sessionUser.getDomainId());
		Page page = (Page) request.getAttribute(PAGE);
		Map<String, Object> pageAttr = new HashMap<String, Object>();
		pageAttr.put("roleName", role.getRoleName());
		page.setPageAttrs(pageAttr);
		ModelAndView mv = new ModelAndView("role/role_list");
		int totalRecords = roleService.findAllRolesCount(role);
		page.setTotalRecords(totalRecords);
		page.setPageCount(getPageCount(page));
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		mv.addObject("roles", roleService.findAllRolesRole(role, rb));
		return mv;

	}
	@AuthTarget({"addRole"})
	@RequestMapping(value = "add_role", method = RequestMethod.GET)
	public String preAddRole() {
		return "role/role_add";
	}
	@AuthTarget({"updateRole"})
	@RequestMapping(value = "update/{roleId}", method = RequestMethod.GET)
	public ModelAndView perUpdateRole(@PathVariable("roleId") Integer roleId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("role/role_update");
		Role role = this.roleService.findOneById(roleId);

		List<Map<String, Object>> perms = this.authorityService
				.findPermsByRole(role);

		mv.addObject("role", role);
		mv.addObject("perms", perms);
		if (null == role) {
			// TODO 404
			return null;
		} else {
			return mv;

		}

	}

	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Object updateRole(
			@RequestBody List<Map<String, Object>> moudleAndPerms,
			HttpSession session) {

		OperateResult op = new OperateResult();
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		try {
			Object roleName = moudleAndPerms.get(0).get("roleName");
			List<String> ost_moudle_users = (List<String>) moudleAndPerms
					.get(1).get(OST_MOUDLES.ost_moudle_users.toString());
			List<String> ost_moudle_depts = (List<String>) moudleAndPerms
					.get(2).get(OST_MOUDLES.ost_moudle_depts.toString());
			List<String> ost_moulde_roles = (List<String>) moudleAndPerms
					.get(3).get(OST_MOUDLES.ost_moulde_roles.toString());
			List<String> ost_moudle_chart = (List<String>) moudleAndPerms
					.get(4).get(OST_MOUDLES.ost_moudle_chart.toString());
			List<String> ost_moudle_project_tag = (List<String>) moudleAndPerms
					.get(5).get(OST_MOUDLES.ost_moudle_p_tags.toString());
			List<String> ost_moudle_customer_tag = (List<String>) moudleAndPerms
					.get(6).get(OST_MOUDLES.ost_moudle_c_tags.toString());
			List<String> ost_moudle_worktime =  (List<String>) moudleAndPerms.get(7).get(OST_MOUDLES.ost_moudle_worktime.toString());

			List<String> ost_app_moudle_events = (List<String>) moudleAndPerms
					.get(8).get(OST_MOUDLES.ost_app_moudle_events.toString());

			List<String> ost_app_moudle_my = (List<String>) moudleAndPerms.get(
					9).get(OST_MOUDLES.ost_app_moudle_my.toString());
			
			List<String> ost_app_moudle_find = (List<String>) moudleAndPerms.get(
					10).get(OST_MOUDLES.ost_app_moudle_find.toString());
			
			
			Object roleId = moudleAndPerms.get(11).get("roleId");
			Object level = moudleAndPerms.get(12).get("level");

			List<String> methodNames = new ArrayList<String>();

			if ((null == roleName || roleName.toString().equals(""))
					|| (null == level || level.toString().equals(""))
					|| (null == roleId || roleId.toString().equals(""))) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("roleName or level or roleId  must be provided!");
				op.setData(null);
				return op;
			}

			if (null != ost_moudle_users) {
				methodNames.addAll(ost_moudle_users);
			}

			if (null != ost_moudle_depts) {
				methodNames.addAll(ost_moudle_depts);
			}
			if (null != ost_moulde_roles) {
				methodNames.addAll(ost_moulde_roles);
			}
			if (null != ost_moudle_chart) {
				methodNames.addAll(ost_moudle_chart);
			}
			if (null != ost_moudle_project_tag) {
				methodNames.addAll(ost_moudle_project_tag);
			}
			if (null != ost_moudle_customer_tag) {
				methodNames.addAll(ost_moudle_customer_tag);
			}
			
			if(null != ost_moudle_worktime){
				methodNames.addAll(ost_moudle_worktime);
			}
			if (null != ost_app_moudle_my) {
				methodNames.addAll(ost_app_moudle_my);
			}
			if (null != ost_app_moudle_events) {
				methodNames.addAll(ost_app_moudle_events);
			}
			
			if(null != ost_app_moudle_find){
				
				methodNames.addAll(ost_app_moudle_find);

			}
			

			List<Map<String, Object>> perms = this.authorityService
					.findMoudleIdByPermTag(methodNames);
			Role role = new Role();
			role.setRoleId(Integer.valueOf(roleId.toString()));
			role.setUpdateTime(new Date());
			role.setUpdator(sessionUser.getRealname());
			role.setRoleName(roleName.toString());
			role.setLevel(Byte.valueOf(level.toString()));
			this.roleService.updateRole(perms, role);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("update role  success");
			op.setData(null);

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("添加 角色 失败", e);

			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}

		return op;
	}

	@ResponseBody
	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	public Object addRole(
			@RequestBody List<Map<String, Object>> moudleAndPerms,
			HttpSession session) {

		OperateResult op = new OperateResult();
		Map<String, Object> sessionLocal = (Map<String, Object>) sessionStroe(
				session, Constants.SESSION_USER);
		User sessionUser = (User) sessionLocal.get("user");
		try {
			Object roleName = moudleAndPerms.get(0).get("roleName");
			List<String> ost_moudle_users = (List<String>) moudleAndPerms
					.get(1).get(OST_MOUDLES.ost_moudle_users.toString());
			List<String> ost_moudle_depts = (List<String>) moudleAndPerms
					.get(2).get(OST_MOUDLES.ost_moudle_depts.toString());
			List<String> ost_moulde_roles = (List<String>) moudleAndPerms
					.get(3).get(OST_MOUDLES.ost_moulde_roles.toString());
			List<String> ost_moudle_chart = (List<String>) moudleAndPerms
					.get(4).get(OST_MOUDLES.ost_moudle_chart.toString());
			List<String> ost_moudle_project_tag = (List<String>) moudleAndPerms
					.get(5).get(OST_MOUDLES.ost_moudle_p_tags.toString());
			List<String> ost_moudle_customer_tag = (List<String>) moudleAndPerms
					.get(6).get(OST_MOUDLES.ost_moudle_c_tags.toString());

			
			List<String> ost_moudle_worktime =  (List<String>) moudleAndPerms.get(7).get(OST_MOUDLES.ost_moudle_worktime.toString());
			
			List<String> ost_app_moudle_events = (List<String>) moudleAndPerms
					.get(8).get(OST_MOUDLES.ost_app_moudle_events.toString());

			List<String> ost_app_moudle_my = (List<String>) moudleAndPerms.get(
					9).get(OST_MOUDLES.ost_app_moudle_my.toString());
			List<String> ost_app_moudle_find = (List<String>) moudleAndPerms.get(
					10).get(OST_MOUDLES.ost_app_moudle_find.toString());
			List<String> methodNames = new ArrayList<String>();
			Object level = moudleAndPerms.get(12).get("level");

			if ((null == roleName || roleName.toString().equals(""))
					||(null == level || level.toString().equals(""))) {

				op.setStatusCode(Constants.PARAMTERS_NOT_COMPLETE);
				op.setDescription("roleName must be provided!");
				op.setData(null);
				return op;
			}

			if (null != ost_moudle_users) {
				methodNames.addAll(ost_moudle_users);
			}

			if (null != ost_moudle_depts) {
				methodNames.addAll(ost_moudle_depts);
			}
			if (null != ost_moulde_roles) {
				methodNames.addAll(ost_moulde_roles);
			}
			if (null != ost_moudle_chart) {
				methodNames.addAll(ost_moudle_chart);
			}
			if (null != ost_moudle_project_tag) {
				methodNames.addAll(ost_moudle_project_tag);
			}
			if (null != ost_moudle_customer_tag) {
				methodNames.addAll(ost_moudle_customer_tag);
			}
			
			if(null != ost_moudle_worktime){
				methodNames.addAll(ost_moudle_worktime);
			}
			
			if (null != ost_app_moudle_my) {
				methodNames.addAll(ost_app_moudle_my);
			}
			if (null != ost_app_moudle_events) {
				methodNames.addAll(ost_app_moudle_events);
			}
			
			if (null != ost_app_moudle_find) {
				methodNames.addAll(ost_app_moudle_find);
			}
			
			List<Map<String, Object>> perms = this.authorityService
					.findMoudleIdByPermTag(methodNames);
			Role role = new Role();
			role.setCreateTime(new Date());
			role.setUpdateTime(new Date());
			role.setCreator(sessionUser.getRealname());
			role.setUpdator(sessionUser.getRealname());
			role.setDomainId(sessionUser.getDomainId());
			role.setRoleName(roleName.toString());
			role.setLevel(Byte.valueOf(level.toString()));
			this.roleService.addRole(perms, role);

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription(" add role  success");
			op.setData(null);

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("添加 角色 失败", e);

			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription(Constants.SERVER_ERROR_TIP);
			op.setData(null);
		}

		return op;
	}
}
