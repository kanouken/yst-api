package org.ost.crm.dao.auth;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 角色  可操作模块 ，模块 权限 
 * @author xnq
 *
 */
public interface RolesMoudlesPermsMapper {
		public List<Map<String, Object>> selectMoudlesByRole(@Param("roleId") Integer roleId,@Param("moudleType")  Byte moudleType);
		public List<Map<String, Object>> selectPermsByMethodName(@Param("methodNames") List<String> methodNames);

		public List<Map<String, Object>> selectPermsByRole(@Param("roleId") Integer roleId);
		/**
		 * 
		 * @param roleId 角色 id
		 * @param permType app 的 权限 还是 后台的权限 
		 * @return
		 */
		public List<Map<String, Object>> selectPermsByRoleAndType(@Param("roleId") Integer roleId , @Param("permType") Byte permType);
		
		
		
		public void insertRoleMoudlePerm(@Param("perms") List<Map<String, Object>> perms,@Param("roleId") Integer roleId);
		public void deleteRoleMoudlePerm(@Param("roleId") Integer roleId);
}
