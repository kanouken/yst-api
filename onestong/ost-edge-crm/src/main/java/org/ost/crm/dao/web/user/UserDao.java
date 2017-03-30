package org.ost.crm.dao.web.user;

import org.apache.ibatis.annotations.Param;
import org.ost.crm.model.web.user.UserDto;
import org.ost.entity.user.Users;

import tk.mybatis.mapper.common.Mapper;

public interface UserDao extends Mapper<Users>{
	
	//登录
	UserDto selectLogin(@Param(value = "email") String email,@Param(value = "password") String password);
	UserDto selectByEmail(@Param(value = "email") String email);
	
}