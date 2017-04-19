package org.ost.crm.services.web.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.tools.exception.ApiException;
import org.ost.crm.dao.auth.RolesMoudlesPermsMapper;
import org.ost.crm.dao.auth.UsersRolesMapper;
import org.ost.crm.dao.web.user.UserDao;
import org.ost.crm.model.web.user.AccountDto;
import org.ost.crm.model.web.user.UserDto;
import org.ost.crm.services.auth.AuthorityService;
import org.ost.crm.services.department.DepartmentService;
import org.ost.entity.auth.Role;
import org.ost.entity.org.department.Departments;
import org.ost.entity.user.Users;
import org.ost.entity.user.UsersRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	UsersRolesMapper userRoleMapper;
	@Autowired
	RolesMoudlesPermsMapper rolePermsMapper;

	@Autowired
	DepartmentService departmentService;

	/**
	 * 管理后台web登录
	 * 
	 * @param email
	 *            登录用户名
	 * @param password
	 *            登录密码 md5
	 * @return 用户信息
	 * @author chenyingwen
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public Map<String, Object> login(String email, String password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserDto userDto = this.userDao.selectByEmail(email);
		if (null == userDto) {
			throw new ApiException("用户不存在", "");
		}
		AccountDto accountDto = new AccountDto();
		accountDto.setLoginName(userDto.getEmail());
		accountDto.setLoginPassword(password);
		if (!accountDto.getLoginPassword().equals(password)) {
			throw new ApiException("密码不正确", "");
		}
		userDto = this.userDao.selectLogin(email, password);
		resultMap.put("userId", userDto.getUserId());
		resultMap.put("realName", userDto.getRealName());
		resultMap.put("email", userDto.getEmail());
		resultMap.put("deptId", userDto.getDeptId());
		resultMap.put("deptName", userDto.getDeptName());
		resultMap.put("schemaId", userDto.getDomainId());
		resultMap.put("isDirector", userDto.getIsDirector());
		// 角色 &权限
		Role role = userRoleMapper.selectRolesByUser(userDto.getUserId()).get(0);
		List<Map<String, Object>> perms = this.rolePermsMapper.selectPermsByRoleAndType(role.getRoleId(),
				AuthorityService.MOUDLE_ADMIN);
		resultMap.put("token",
				Jwts.builder().setSubject("1st").claim("userId", userDto.getUserId())
						.claim("realName", userDto.getRealName()).claim("email", userDto.getEmail())
						.claim("deptId", userDto.getDeptId()).claim("deptName", userDto.getDeptName())
						.claim("schemaId", userDto.getDomainId()).claim("isDirector", userDto.getIsDirector())
						.claim("role", role).claim("perms", perms).setIssuedAt(new Date())
						.signWith(SignatureAlgorithm.HS256, "1stapp").compact());
		resultMap.put("role", role);
		return resultMap;
	}

	@Transactional(readOnly = true)
	public List<Users> findUsersByIds(List<Integer> userIds) {
		List<Users> users = this.userDao.selectByIds(userIds);
		return users;
	}

	@SuppressWarnings("all")
	@Transactional(readOnly = true)
	public List<Users> findUserScopes(Users user) {
		// 查看所有的事件
		if (user.getRole().getRoleCode().equals(UsersRole.ADMIN)) {
			Users users = new Users();
			users.setSchemaId(user.getSchemaId());
			return userDao.select(users);
			// 部门管理员
		} else if (user.getRole().getRoleCode().equals(UsersRole.DEPARTMENT_MANAGER)) {
			Departments dept = new Departments();
			dept.setDeptId(user.getDeptId());
			List<Departments> departments = departmentService.queryDepartmentRecursion(dept);
			return this.userDao.selectByDeptIds(departments);

		} else {
			return new ArrayList<Users>() {
				{
					add(user);
				}
			};
		}

	}

}
