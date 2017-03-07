package org.ost.entity.project.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.ost.entity.project.Project;
import org.ost.entity.project.ProjectPayment;
import org.ost.entity.project.ProjectStep;
import org.ost.entity.project.ProjectType;
import org.ost.entity.project.ProjectTypeStep;
import org.ost.entity.project.dto.ProjectCreateOrUpdateDto;
import org.ost.entity.project.dto.ProjectPaymentDto;
import org.ost.entity.project.dto.ProjectStepsDetailDto;
import org.ost.entity.project.dto.ProjectStepsDto;
import org.ost.entity.project.dto.ProjectTypeDto;

@Mapper
public interface ProjectEntityMapper {
	ProjectEntityMapper INSTANCE = Mappers.getMapper(ProjectEntityMapper.class);

	@Mappings({ @Mapping(source = "typeID", target = "projectTypeID") })
	Project createOrUpateDtoToProject(ProjectCreateOrUpdateDto dto);

	@Mapping(source = "timeStr", target = "time", dateFormat = "yyyy-MM-dd")
	ProjectPayment projectPaymentDtoToProjectPayment(ProjectPaymentDto ppdto);

	@Mappings({
			@Mapping(target = "cycWarning", expression = "java(new java.util.HashMap<String,Object>(){ {put(\"day\",pType.getCycWarningDay());"
					+ "put(\"isEnable\",pType.getCycWarningEnable()); } })"),
			@Mapping(target = "startTimeWarning", expression = "java(new java.util.HashMap<String,Object>(){ {put(\"day\",pType.getStartTimeWarningDay());"
					+ "put(\"isEnable\",pType.getStartTimeWarningEnable()); } })"),
			@Mapping(source = "id", target = "typeID"), @Mapping(source = "name", target = "typeName") })
	ProjectStepsDetailDto projectTypeToProjectStepDetailDto(ProjectType pType);

	@Mappings({
			@Mapping(target = "warning", expression = "java(new java.util.HashMap<String,Object>(){ {put(\"day\",projectTypeStep.getDay());"
					+ "put(\"isEnable\",projectTypeStep.getIsEnable()); } })") })
	ProjectStepsDto projectTypeStepToProjectStepDto(ProjectTypeStep projectTypeStep);

	List<ProjectStepsDto> projectTypeStepToProjectStepDto(List<ProjectTypeStep> projectTypeSteps);

	ProjectTypeDto projectTypeToProjectTypeDto(ProjectType ptype);

	List<ProjectTypeDto> projectTypesToProjectTypeDtos(List<ProjectType> pTypes);

	@Mappings({ @Mapping(source = "timeStr", target = "time", dateFormat = "yyyy-MM-dd"),
			@Mapping(source = "id", target = "projectTypeStepID"),
			@Mapping(target="id",ignore=true)
	})
	ProjectStep projectStepsDtoToProjectStep(ProjectStepsDto dtos);

	List<ProjectStep> projectStepsDtoToProjectStep(List<ProjectStepsDto> dtos);

}
