package org.ost.crm.model.visit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.ost.crm.model.visit.Visit;
import org.ost.crm.model.visit.VisitApprover;
import org.ost.crm.model.visit.VisitAttence;
import org.ost.crm.model.visit.VisitProject;
import org.ost.crm.model.visit.VisitSupporter;
import org.ost.crm.model.visit.dto.CreateVisitDto;
import org.ost.crm.model.visit.dto.VisitAttenceCreateDto;
import org.ost.crm.model.visit.dto.VisitAttenceDto;
import org.ost.crm.model.visit.dto.VisitDetailDto;
import org.ost.entity.project.dto.ProjectListDto;
import org.ost.entity.user.Users;
import org.ost.entity.user.dto.UserListDto;

import com.hp.hpl.sparta.xpath.TrueExpr;

@Mapper
public interface VisitEntityMapper {
	VisitEntityMapper INSTANCE = Mappers.getMapper(VisitEntityMapper.class);

	@Mapping(target = "createTime", expression = "java(new java.util.Date())")
	@Mapping(target = "updateTime", expression = "java(new java.util.Date())")
	@Mapping(target = "createBy", source = "currentUser.realname")
	@Mapping(target = "updateBy", source = "currentUser.realname")
	@Mapping(target = "isDelete", constant = "0")
	@Mapping(target = "visitTime", source = "createVisitDto.visitTime", dateFormat = "yyyy-MM-dd HH:MM")
	@Mapping(target = "createId", source = "currentUser.userId")
	@Mapping(target = "updateId", source = "currentUser.userId")
	@Mapping(target = "approvalStatus", expression = "java(org.ost.crm.model.visit.VisitApprovalFlow.NO_PASS.getState())")
	Visit combineCreateVisitDtoAndUsersToVisit(CreateVisitDto createVisitDto, Users currentUser);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "visitEventID", source = "visit.id")
	@Mapping(target = "projectID", source = "project.id")
	VisitProject combineProjectListDtoAndVisitToVisitProject(ProjectListDto project, Visit visit);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "visitEventID", source = "visit.id")
	@Mapping(target = "role", constant = "1")
	@Mapping(target = "userID", source = "user.id")
	@Mapping(target = "organizeID", source = "user.deptID")
	@Mapping(target = "organizeName", source = "user.deptName")
	@Mapping(target = "userName", source = "user.name")
	VisitSupporter combineSupporterDtoAndVisitToVisitSupporter(UserListDto user, Visit visit);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "visitEventID", source = "visit.id")
	@Mapping(target = "role", constant = "0")
	@Mapping(target = "userID", source = "user.id")
	@Mapping(target = "organizeID", source = "user.deptID")
	@Mapping(target = "organizeName", source = "user.deptName")
	@Mapping(target = "userName", source = "user.name")
	@Mapping(target = "approvalStatus", expression = "java(org.ost.crm.model.visit.VisitApprovalFlow.NO_PASS.getState())")
	VisitApprover combineUserListDtoAndVisitToVisitApprover(UserListDto user, Visit visit);

	@Mapping(target = "createTime", expression = "java(new java.util.Date())")
	@Mapping(target = "updateTime", expression = "java(new java.util.Date())")
	@Mapping(target = "createBy", source = "currentUser.realname")
	@Mapping(target = "updateBy", source = "currentUser.realname")
	@Mapping(target = "isDelete", constant = "0")
	@Mapping(target = "createId", source = "currentUser.userId")
	@Mapping(target = "updateId", source = "currentUser.userId")
	@Mapping(target = "state", ignore = true)
	VisitAttence combineCreateVisitAttenceAndCurrentUserToVisitAttence(VisitAttenceCreateDto visitAttenceCreateDto,
			Users currentUser);

	@Mapping(expression = "java( org.ost.entity.tools.JsonType.convertToList(vsSupporter.getVisitContent()))", target = "visitContent")
	@Mapping(target = "id", source = "visit.id")
	@Mapping(target = "createTime", source = "visit.createTime")
	@Mapping(target = "createTimeStr", source = "visit.createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
	@Mapping(target = "visitTimeStr", source = "visit.visitTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
	VisitDetailDto combineVisitAndSupporterToVisitDetailDto(Visit visit, VisitSupporter vsSupporter);

	@Mapping(target = "signInTimeStr", source = "signInTime", dateFormat = "yyyy-MM-dd HH:mm")
	@Mapping(target = "signOutTimeStr", source = "signOutTime", dateFormat = "yyyy-MM-dd HH:mm")
	VisitAttenceDto visitAttenceToVisitAttenceDto(VisitAttence visitAttence);

}
