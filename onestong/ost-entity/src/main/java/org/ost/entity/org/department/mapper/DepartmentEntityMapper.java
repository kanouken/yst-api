package org.ost.entity.org.department.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.org.department.Departments;
import org.ost.entity.org.department.dto.DepartmentListDto;

@Mapper
public interface DepartmentEntityMapper {
	DepartmentEntityMapper INSTANCE = Mappers.getMapper(DepartmentEntityMapper.class);

	@Mappings({ @Mapping(source = "deptId", target = "id"), @Mapping(source = "name", target = "name"),
			@Mapping(source = "parentId", target = "parentId"), @Mapping(source = "isLeaf", target = "isLeaf") })
	DepartmentListDto departmentToDepartmentListDto(Departments dept);
	
	List<DepartmentListDto> departmentsToDepartmentListDtos(List<Departments> depts);

}
