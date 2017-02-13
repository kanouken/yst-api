package org.ost.edge.onestong.controller.api.departments;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.common.tools.OperateResult;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.controller.base.Action.OST_APP_PERM;
import org.ost.edge.onestong.model.authority.Role;
import org.ost.edge.onestong.model.department.Department;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.authority.AuthorityService;
import org.ost.edge.onestong.services.web.department.DepartmentService;
import org.ost.edge.onestong.services.web.user.UsersService;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/department")
public class DepartmentDataApi extends Action {
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private UsersService userService;
	@Autowired
	private AuthorityService authorityService;

	/**
	 * 获取最根上部门列表 需要传递 用户 id
	 * 
	 * 
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "{userId}/top/{token}", method = RequestMethod.GET)
	public Object getTop(@PathVariable("token") String token,
			@PathVariable("userId") Integer userid) {
		// return this.departmentService.findAllLeve1Departments();

		OperateResult op = new OperateResult();
		User user = this.userService.findOneById(userid);
		try {
			List<Department> depts = this.departmentService
					.findAllLeve1Departments(user.getDomainId());

			if (CollectionUtils.isNotEmpty(depts)) {
				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("get all level1 departments success");
				op.setData(depts);

			} else {

				op.setStatusCode(Constants.HTTP_200);
				op.setDescription("no departments!");
				op.setData(null);
			}

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("获取一级部门 失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("server has gone away,try again later!");
			op.setData(null);
		}

		return op;
	}

	/**
	 * 一级 一级获取 部门成员 或者 下级部门 或者 下级部门与直属领导人
	 * 
	 * @param parentId
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "childrenOrMembers/{parentId}/{token}", method = RequestMethod.GET)
	public Object lookForChild(@PathVariable("parentId") Integer parentId,
			@PathVariable("token") String token) {

		// return
		// this.departmentService.findAllChildorMembersByParentId(parentId);

		OperateResult op = new OperateResult();

		try {

			op.setStatusCode(Constants.HTTP_200);
			op.setDescription("get all childrenOrMembers success");
			op.setData(this.departmentService
					.findAllChildorMembersByParentId(parentId));

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {

				logger.error("获取下级部门 或者 部门成员失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("server has gone away,try again later!");
			op.setData(null);
		}

		return op;
	}

	/**
	 * 获取所有domain 部门
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "all/{userId}/{token}", method = RequestMethod.GET)
	public Object allDepartment(@PathVariable("userId") Integer userId,
			@PathVariable("token") String token) {

		OperateResult op = new OperateResult();
		User user = null;
		try {

			user = this.userService.findOneById(userId);
			if (null == user) {

				if (logger.isErrorEnabled()) {
					logger.error("获取所有domain 部门 --- 用户不存在");

				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}

			Department d = new Department();
			d.setDomainId(user.getDomainId());
			List<Map<String, Object>> depts = this.departmentService.findAllDepartmentsAndParentNameFilterByDomain(user.getDomainId());
			
			op.setStatusCode(HTTP_200);
			op.setDescription("get all departments success!");
			op.setData(depts);
			
		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error(
						"获取所有 domain 部门失败 domain name=" + user.companyName, e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;
	}

	/**
	 * 
	 * 根据用户权限判断在 发现 模块 中 轨迹地图 与快捷报表 中 可以查看的部门
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "discory/report/{userId}/{token}", method = RequestMethod.GET)
	public Object method(@PathVariable("userId") Integer userId,
			@PathVariable("token") String token) {

		OperateResult op = new OperateResult();
		User user = null;
		try {
			user = this.userService.findOneById(userId);
			if (null == user) {
				if (logger.isErrorEnabled()) {
					logger.error("查看快捷报表、轨迹地图 --- 用户不存在");
				}
				op.setStatusCode(Constants.USER_NOT_FOUND);
				op.setDescription("user can not found!");
				return op;
			}
			Role role = this.authorityService.findRoleByUser(user);
			List<Map<String, Object>> perms = this.authorityService
					.findPermsByRoleAndType(role, Constants.MOUDLE_APP);
			// 总 boss
			if (AuthorityService.isPermExits(OST_APP_PERM.all_reports, perms)) {

				// 直接返回该domain 下的所有部门 不管你是什么部门
				Department d = new Department();
				d.setDomainId(user.getDomainId());
				// List<Department> depts = this.departmentService
				// .findAllLeve1Departments(user.getDomainId());
				List<Department> allDepts = this.departmentService
						.findAllDepartments(d);

				op.setStatusCode(HTTP_200);
				op.setDescription("get root departments success!");
				op.setData(allDepts);
				return op;

				// 区域经理 或其他
			} else if (AuthorityService.isPermExits(
					OST_APP_PERM.self_below_reports, perms)) {
				// TODO 这里可以让 客户端取判断权限
				Department myDept = this.departmentService.findOne(user
						.getDeptId());
				op.setStatusCode(HTTP_200);
				op.setDescription("get own's blow departments success!");
				op.setData(myDept);
			} else {

			}

		} catch (Exception e) {

			if (logger.isErrorEnabled()) {
				logger.error("按用户权限获取可查看部门失败", e);
			}
			op.setStatusCode(Constants.SERVER_ERROR);
			op.setDescription("服务器异常！稍后再试");
			op.setData(null);
		}

		return op;

	}
}
