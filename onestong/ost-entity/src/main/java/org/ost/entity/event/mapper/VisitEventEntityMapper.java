package org.ost.entity.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.event.VisitEvents;
import org.ost.entity.event.vo.VisitEventCreateVo;
import org.ost.entity.user.Users;

@Mapper
public interface VisitEventEntityMapper {
	VisitEventEntityMapper INSTANCE = Mappers.getMapper(VisitEventEntityMapper.class);

	@Mappings({ @Mapping(source = "u.userId", target = "userId"), @Mapping(source = "u.realname", target = "creator"),
			@Mapping(source = "u.realname", target = "updator"), @Mapping(source = "vo.customer.id", target = "customerId"),
			@Mapping(constant = "1", target = "valid"), @Mapping(source = "vo.customer.name", target = "customerName"),
			@Mapping(source = "vo.project.id", target = "projectId"),
			@Mapping(source = "vo.project.name", target = "projectName"),
			@Mapping(source = "vo.contactType", target = "contactType"),
			@Mapping(source = "vo.contactTime",dateFormat="yyyy-MM-dd HH:mm", target = "contactTime"), 
			@Mapping(source = "vo.busChange", target = "busChange"),
			@Mapping(target = "createTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "state", constant = "0"),
			@Mapping(target = "veId", expression = "java(java.util.UUID.randomUUID().toString())") })
	VisitEvents visitEventCreateVoToVisitEvents(VisitEventCreateVo vo, Users u);

}
