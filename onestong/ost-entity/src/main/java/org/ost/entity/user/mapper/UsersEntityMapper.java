package org.ost.entity.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.ost.entity.user.Users;
import org.ost.entity.user.dto.UserListDto;

@Mapper
public interface UsersEntityMapper {
	UsersEntityMapper INSTANCE = Mappers.getMapper(UsersEntityMapper.class);

	@Mapping(source="realname",target="name")
	@Mapping(source="deptId",target="deptID")
	@Mapping(source="departmentName",target="deptName")
	@Mapping(source="userId",target="id")
	UserListDto usersToUserListDto(Users user);

	List<UserListDto> usersToUserListDto(List<Users> uesrs);
}
