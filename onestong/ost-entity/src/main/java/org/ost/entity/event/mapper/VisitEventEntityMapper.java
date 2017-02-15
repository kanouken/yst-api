package org.ost.entity.event.mapper;

import java.util.Random;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.event.VisitEvents;
import org.ost.entity.event.vo.VisitEventVo;
import org.ost.entity.user.User;

@Mapper
public interface VisitEventEntityMapper {
	VisitEventEntityMapper INSTANCE = Mappers.getMapper(VisitEventEntityMapper.class);

	@Mappings({ @Mapping(source = "u.id", target = "userId"), @Mapping(source = "u.name", target = "creator"),
			@Mapping(source = "u.name", target = "updator"), @Mapping(source = "vo.customer.id", target = "customerId"),
			@Mapping(constant = "1", target = "valid"), @Mapping(source = "vo.customer.name", target = "optional1"),
			@Mapping(target = "createTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "state", constant = "0"),
			@Mapping(target = "veId", expression = "java(java.util.UUID.randomUUID().toString())") })
	VisitEvents visitEventVoToVisitEvents(VisitEventVo vo, User u);
	
}
