package org.ost.entity.event.mapper;

import java.util.List;

import javax.xml.transform.Source;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.event.approval.ApprovalEvent;
import org.ost.entity.event.approval.dto.ApprovalEventDto;
import org.ost.entity.event.approval.dto.ApprovalListDto;

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

	@Mappings({

			@Mapping(source = "createTime", dateFormat = "yyyy-MM-dd HH:mm", target = "createTime"),
			@Mapping(source = "aeId", target = "eId"),
			@Mapping(source = "state", target = "status")
	})
	ApprovalListDto approvalEventToApprovalListDto(ApprovalEvent aEvent);

	List<ApprovalListDto> approvalEventToApprovalListDto(List<ApprovalEvent> aevents);
}
