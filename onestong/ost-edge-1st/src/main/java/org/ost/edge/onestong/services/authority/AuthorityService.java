package org.ost.edge.onestong.services.authority;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.ost.edge.onestong.controller.base.Action.OST_APP_PERM;
import org.ost.edge.onestong.controller.base.Action.OST_OST_PERM;
import org.ost.edge.onestong.dao.authority.RolesMoudlesPermsMapper;
import org.ost.edge.onestong.dao.authority.UsersRolesMapper;
import org.ost.edge.onestong.model.authority.Role;
import org.ost.edge.onestong.model.user.User;
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
	public Role findRoleByUser(User user) {
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

		return this.rolesMoudlesPermsMapper.selectMoudlesByRole(
				role.getRoleId(), moudleType);
	}

	/**
	 * 根据 权限的 访问的方法名找到对应的 moudle
	 * 
	 * @param role
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findMoudleIdByPermTag(
			List<String> methodNames) {

		return this.rolesMoudlesPermsMapper
				.selectPermsByMethodName(methodNames);
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
		this.rolesMoudlesPermsMapper.insertRoleMoudlePerm(perms,
				role.getRoleId());

	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findPermsByRoleAndType(Role role, Byte type) {

		return this.rolesMoudlesPermsMapper.selectPermsByRoleAndType(
				role.getRoleId(), type);
	}

	public static boolean isPermExits(OST_APP_PERM ost_perm,
			List<Map<String, Object>> perms) {

		if(null == perms){
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

	public static boolean isPermExits(OST_OST_PERM ost_perm,
			List<Map<String, Object>> perms) {
		if(null == perms){
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
	
	

}
