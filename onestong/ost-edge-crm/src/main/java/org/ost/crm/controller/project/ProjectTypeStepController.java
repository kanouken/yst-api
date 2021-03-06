package org.ost.crm.controller.project;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.project.ProjectService;
import org.ost.crm.services.project.ProjectTypeService;
import org.ost.crm.services.project.ProjectTypeStepService;
import org.ost.entity.project.ProjectTypeStep;
import org.ost.entity.project.dto.ProjectStepsDetailDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "projectTypeStep")
public class ProjectTypeStepController extends Action {

	@Autowired
	public ProjectTypeStepService projectTypeStepService;
	@Autowired
	public ProjectService projectService;
	

	@ApiOperation(value = "新增项目类型阶段", notes = "新增项目类型阶段")
	@PostMapping(value = "{projectTypeID}")
	public OperateResult<String> CreateProjectTypeSteps(
			@PathVariable(value = "projectTypeID") Integer projectTypeID,
			@RequestAttribute(value = LOGIN_USER) Users users, 
			@RequestBody ProjectStepsDetailDto pdto) {
		return new OperateResult<String>(projectTypeStepService.createOrUpdateTypeStep(projectTypeID, users, pdto));
	}

	@ApiOperation(value = "编辑项目类型阶段", notes = "编辑项目类型阶段")
	@PutMapping(value = "{projectTypeID}")
	public OperateResult<String> updateProjectTypeSteps(
			@PathVariable(value = "projectTypeID") Integer projectTypeID,
			@RequestAttribute(value = LOGIN_USER) Users users, 
			@RequestBody ProjectStepsDetailDto pdto) {
		return new OperateResult<String>(projectTypeStepService.createOrUpdateTypeStep(projectTypeID, users, pdto));
	}

	@ApiOperation(value = "项目类型阶段详情", notes = "项目类型阶段详情")
	@GetMapping(value = "{projectTypeID}")
	public OperateResult<ProjectStepsDetailDto> detailProjectTypeSteps(
			@PathVariable(value = "projectTypeID") Integer projectTypeID,
			@RequestAttribute(value = LOGIN_USER) Users users) {
		return new OperateResult<ProjectStepsDetailDto>(projectService.queryProjectSteps(projectTypeID, users));
		 
	}

}
