package org.ost.entity.project.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import org.ost.entity.org.department.dto.DepartmentListDto;
import org.ost.entity.project.Project;
import org.ost.entity.project.ProjectFile;
import org.ost.entity.project.ProjectOrg;
import org.ost.entity.project.ProjectPayment;
import org.ost.entity.project.ProjectStep;
import org.ost.entity.project.ProjectType;
import org.ost.entity.project.ProjectTypeStep;
import org.ost.entity.project.UserProject;
import org.ost.entity.project.dto.ProjectCreateOrUpdateDto;
import org.ost.entity.project.dto.ProjectDetailDto;
import org.ost.entity.project.dto.ProjectFileDto;
import org.ost.entity.project.dto.ProjectPaymentDto;
import org.ost.entity.project.dto.ProjectStepsDetailDto;
import org.ost.entity.project.dto.ProjectStepsDto;
import org.ost.entity.project.dto.ProjectTypeDto;
import org.ost.entity.user.Users;
import org.ost.entity.user.dto.UserListDto;

@Mapper
public interface ProjectEntityMapper {
	ProjectEntityMapper INSTANCE = Mappers.getMapper(ProjectEntityMapper.class);

	@Mappings({ @Mapping(source = "typeID", target = "projectTypeID"), @Mapping(source = "details", target = "detail"),
			@Mapping(source = "startTimeStr", target = "startTime", dateFormat = "yyyy-MM-dd"),
			@Mapping(target="isCyc",expression="java(dto.getIsCyc()==null ||dto.getIsCyc().equals(\"\") ? 0 :Integer.valueOf(dto.getIsCyc() ))"),
			@Mapping(target="cyc",expression="java(dto.getCyc()==null ||dto.getCyc().equals(\"\") ? 0 :Integer.valueOf(dto.getCyc() ))")

	})
	Project createOrUpateDtoToProject(ProjectCreateOrUpdateDto dto);

	@Mapping(source = "timeStr", target = "time", dateFormat = "yyyy-MM-dd")
	@Mapping(target="id" ,ignore=true)
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
					+ "put(\"isEnable\",projectTypeStep.getIsEnable()); } })"),
			@Mapping(source="time",target="timeStr" ,dateFormat="yyyy-MM-dd")
	
	})
	ProjectStepsDto projectTypeStepToProjectStepDto(ProjectTypeStep projectTypeStep);

	List<ProjectStepsDto> projectTypeStepToProjectStepDto(List<ProjectTypeStep> projectTypeSteps);

	ProjectTypeDto projectTypeToProjectTypeDto(ProjectType ptype);

	List<ProjectTypeDto> projectTypesToProjectTypeDtos(List<ProjectType> pTypes);

	@Mappings({ @Mapping(source = "timeStr", target = "time", dateFormat = "yyyy-MM-dd"),
			@Mapping(source = "id", target = "projectTypeStepID"), @Mapping(target = "id", ignore = true) })
	ProjectStep projectStepsDtoToProjectStep(ProjectStepsDto dtos);

	List<ProjectStep> projectStepsDtoToProjectStep(List<ProjectStepsDto> dtos);

	@Mappings({ @Mapping(source = "detail", target = "details"), @Mapping(source = "projectTypeID", target = "typeID"),
			@Mapping(source = "startTime", target = "startTimeStr", dateFormat = "yyyy-MM-dd HH:mm"),
			@Mapping(target = "stateName", expression = "java(org.ost.entity.project.ProjectState.getProjectState(project.getState()).getName())")

	})
	ProjectDetailDto projectToProjectDetailDto(Project project);

	@Mappings({ @Mapping(source = "organizeID", target = "id"), @Mapping(source = "organizeName", target = "name") })
	DepartmentListDto projectOrgToDepartmentListDto(ProjectOrg projectOrgs);

	List<DepartmentListDto> projectOrgToDepartmentListDto(List<ProjectOrg> projectOrgs);

	@Mappings({ @Mapping(source = "userID", target = "id"), @Mapping(source = "organizeID", target = "deptID"),
			@Mapping(source = "organizeName", target = "deptName"), @Mapping(source = "userName", target = "name") })
	UserListDto userProjectToUserListDto(UserProject ups);

	List<UserListDto> userProjectToUserListDto(List<UserProject> ups);

	@Mappings({ @Mapping(source = "time", target = "timeStr", dateFormat = "yyyy-MM-dd"),
			@Mapping(target = "stateName", expression = "java(org.ost.entity.project.ProjectPaymentState.getProjectPaymentState(pps.getState()).getName())")

	})
	ProjectPaymentDto projectPaymentToProjectPaymentDto(ProjectPayment pps);

	List<ProjectPaymentDto> projectPaymentToProjectPaymentDto(List<ProjectPayment> pps);

	@Mappings(

	{ @Mapping(source = "createTime", target = "uploadTime"), @Mapping(source = "createBy", target = "uploader"),
			@Mapping(source = "createTime", target = "uploadTimeStr", dateFormat = "yyyy-MM-dd") })
	ProjectFileDto projectFileToProjectFileDto(ProjectFile pFiles);

	List<ProjectFileDto> projectFileToProjectFileDto(List<ProjectFile> pFiles);

}
