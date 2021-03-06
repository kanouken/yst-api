package org.ost.edge.onestong.services.authority;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.ost.edge.onestong.dao.authority.RoleMapper;
import org.ost.edge.onestong.dao.authority.RolesMoudlesPermsMapper;
import org.ost.edge.onestong.dao.authority.UsersRolesMapper;
import org.ost.edge.onestong.model.authority.Role;
import org.ost.edge.onestong.model.authority.RoleExample;
import org.ost.edge.onestong.model.authority.RoleExample.Criteria;
import org.ost.edge.onestong.tools.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class RoleService {
	
	@Autowired
	private UsersRolesMapper  usersRolesMapper;
	@Autowired
	private RoleMapper  roleMapper;
	@Autowired 
	private RolesMoudlesPermsMapper rolesMoudlesPermsMapper;
	/**
	 * level 大于  自己角色 的level
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Role> findRolesBlow(Role  role){
		RoleExample re = new RoleExample();
		re.createCriteria().andValidEqualTo(Constants.DATA_VALID).andLevelGreaterThan(role.getLevel()).andDomainIdEqualTo(role.getDomainId());
		re.setOrderByClause("update_time desc");
		return this.roleMapper.selectByExample(re);
	}
	@Transactional(readOnly = true)
	public int findAllRolesCount(Role role) {
		RoleExample re = new RoleExample();

		Criteria c = re.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(role.getDomainId());
		
		if (StringUtils.isNotBlank(role.getRoleName())) {

			c.andRoleNameLike("%" + role.getRoleName() + "%");
		}

		return this.roleMapper.countByExample(re);
		
	}
	@Transactional(readOnly = true)
	public Object findAllRolesRole(Role role, RowBounds rb) {

		RoleExample re = new RoleExample();

		Criteria c = re.createCriteria();
		c.andValidEqualTo(Constants.DATA_VALID).andDomainIdEqualTo(role.getDomainId());
		
		if (StringUtils.isNotBlank(role.getRoleName())) {

			c.andRoleNameLike("%" + role.getRoleName() + "%");
		}
		re.setOrderByClause("update_time desc");
		return this.roleMapper.selectByExample(re);
	}
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void addRole(List<Map<String, Object>> perms, Role role) {
		this.roleMapper.insertSelective(role);
		this.rolesMoudlesPermsMapper.insertRoleMoudlePerm(perms, role.getRoleId());
		
	}
	
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateRole(List<Map<String, Object>> perms, Role role) {
		
		RoleExample re = new RoleExample();
		re.createCriteria().andRoleIdEqualTo(role.getRoleId());
		this.roleMapper.updateByExampleSelective(role, re);
		//删除 旧 权限 关联  
		
		this.rolesMoudlesPermsMapper.deleteRoleMoudlePerm(role.getRoleId());
		//插入新的 关联
		this.rolesMoudlesPermsMapper.insertRoleMoudlePerm(perms, role.getRoleId());
		
	}
	
	
	@Transactional(readOnly = true)
	public Role findOneById(Integer roleId) {
		return  this.roleMapper.selectByPrimaryKey(roleId);
	}
	
	
	@Transactional(readOnly = true)
	public Role findRoelByUser(Integer userId) {
		return this.usersRolesMapper.selectRolesByUser(userId).get(0);
	}
}
