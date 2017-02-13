package org.ost.edge.onestong.dao.authority;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ost.edge.onestong.model.authority.Role;

public interface UsersRolesMapper {
	public List<Role> selectRolesByUser(@Param("userId") Integer userId);
	public void insertRolesAndUser(@Param("userId") Integer userId,@Param("roleId") Integer roleId) ;
	public void deleteRolesAndUser(@Param("userId") Integer userId);
}
