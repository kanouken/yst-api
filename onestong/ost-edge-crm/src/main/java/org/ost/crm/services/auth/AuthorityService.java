package org.ost.crm.services.auth;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.ost.crm.dao.auth.RolesMoudlesPermsMapper;
import org.ost.crm.dao.auth.UsersRolesMapper;
import org.ost.entity.auth.Role;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityService {

	@Autowired
	private UsersRolesMapper usersRolesMapper;

	@Autowired
	private RolesMoudlesPermsMapper rolesMoudlesPermsMapper;

	@Transactional(readOnly = true)
	public Role findRoleByUser(Users user) {
		List<Role> roles = usersRolesMapper.selectRolesByUser(user.getUserId());
		// TODO 只处理一种 角色的情况
		return CollectionUtils.isEmpty(roles) ? null : roles.get(0);

	}

	/**
	 * 角色 获取对应 的访问 模块
	 * 
	 * @param role
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findMoudleByRole(Role role, Byte moudleType) {

		return this.rolesMoudlesPermsMapper.selectMoudlesByRole(role.getRoleId(), moudleType);
	}

	/**
	 * 根据 权限的 访问的方法名找到对应的 moudle
	 * 
	 * @param role
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findMoudleIdByPermTag(List<String> methodNames) {

		return this.rolesMoudlesPermsMapper.selectPermsByMethodName(methodNames);
	}

	/**
	 * 角色对应的访问权限
	 * 
	 * @param role
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findPermsByRole(Role role) {

		return this.rolesMoudlesPermsMapper.selectPermsByRole(role.getRoleId());

	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void addMoudleAndPerms(List<Map<String, Object>> perms, Role role) {
		// role id
		this.rolesMoudlesPermsMapper.insertRoleMoudlePerm(perms, role.getRoleId());

	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findPermsByRoleAndType(Role role, Byte type) {

		return this.rolesMoudlesPermsMapper.selectPermsByRoleAndType(role.getRoleId(), type);
	}

	public static boolean isPermExits(OST_APP_PERM ost_perm, List<Map<String, Object>> perms) {

		if (null == perms) {
			return true;
		}

		boolean flag = false;
		for (Map<String, Object> tmp : perms) {
			if (ost_perm.toString().equals(tmp.get("authTag"))) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static boolean isPermExits(OST_OST_PERM ost_perm, List<Map<String, Object>> perms) {
		if (null == perms) {
			return true;
		}
		boolean flag = false;
		for (Map<String, Object> tmp : perms) {
			if (ost_perm.toString().equals(tmp.get("methodName"))) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * TODO cache me
	 * 
	 * @param role
	 *            目标角色
	 * @return 角色对应的用户
	 */
	@Transactional(readOnly = true)
	public List<Users> findUsersByRole(Role role) {
		List<Users> users = this.usersRolesMapper.selectUsersByRole(role);
		return users;
	}

	public static final Byte MOUDLE_ADMIN = 0;//后台 模块
	public static final Byte MOUDLE_APP = 1;// app 模块
	
	public enum OST_APP_PERM {
		self_below_events, // 自己以及下属的事件 ，例如部门管理员 或其他
		all_events, // 所有事件 ，例如总经理 等等
		create_attence, // 创建 签到 签退 事件
		create_visit, // 创建 外访事件
		self_below_reports, // 自己以及自己下属的 报表（快捷报表 与 轨迹地图）
		all_reports

	}

	public enum OST_OST_PERM {
		addUser, deleteUser, unBindUser, userListOwn, userListAll, listChartOwn, listChartAll

	}
}
