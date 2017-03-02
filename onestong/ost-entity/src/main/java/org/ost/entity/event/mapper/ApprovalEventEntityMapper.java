package org.ost.entity.event.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.event.approval.ApprovalEvent;
import org.ost.entity.event.approval.dto.ApprovalEventDto;

@Mapper
public interface ApprovalEventEntityMapper {
	ApprovalEventEntityMapper INSTANCE = Mappers.getMapper(ApprovalEventEntityMapper.class);

	@Mappings({ @Mapping(source = "applyStartTime", dateFormat = "yyyy-MM-dd HH:mm", target = "startTime"),
			@Mapping(source = "applyEndTime", dateFormat = "yyyy-MM-dd HH:mm", target = "endTime"),
			@Mapping(source = "applyType", target = "approvalType"),
			@Mapping(source = "scheduling", target = "schedules"), @Mapping(source = "leaveType", target = "leaveType"),
			@Mapping(target = "aeId", expression = "java(java.util.UUID.randomUUID().toString())"),
			@Mapping(target = "createTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "updateTime", expression = "java(new java.util.Date())"),
			@Mapping(target = "state", constant = "0") })

	ApprovalEvent approvalEventDtoToApprovalEvent(ApprovalEventDto dto);

	@InheritInverseConfiguration
	@Mappings({ @Mapping(source = "createBy", target = "creator"), @Mapping(source = "aeId", target = "approvalEventId")

	})
	ApprovalEventDto approvalEventToApprovalEventDto(ApprovalEvent aEvent);
}
