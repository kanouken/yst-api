package org.ost.crm.dao.auth;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ost.entity.auth.Role;
import org.ost.entity.user.Users;

public interface UsersRolesMapper {
	public List<Role> selectRolesByUser(@Param("userId") Integer userId);
	public void insertRolesAndUser(@Param("userId") Integer userId,@Param("roleId") Integer roleId) ;
	public void deleteRolesAndUser(@Param("userId") Integer userId);
	
	public List<Users> selectUsersByRole(Role role);
}
