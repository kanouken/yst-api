package org.ost.entity.user.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.user.Users;
import org.ost.entity.user.dto.UserListDto;

@Mapper
public interface UserEntityMapper {
	UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

	@Mappings({
			@Mapping(source="userId",target="id"),
			@Mapping(source="realname",target="name"),
			@Mapping(source="departmentName",target="deptName"),
			@Mapping(source="pic",target="headPhoto")
	})
	UserListDto  UserToUserListDto(Users u);
	List<UserListDto>  usersToUserListDtos(List<Users> users);
}
