package org.ost.crm.controller.project;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;

import org.ost.crm.services.project.ProjectTypeService;

import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.project.ProjectType;

import org.ost.entity.project.vo.ProjectTypeVo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiImplicitParam;

import io.swagger.annotations.ApiOperation;

@RestController

@RequestMapping(value = "projectType")

public class ProjectTypeController extends Action {

	@Autowired

	public ProjectTypeService projectTypeService;

	@ApiOperation(value = "新增项目分类", notes = "新增项目分类")
	@ApiImplicitParam(name = "projectType", value = "项目分类", required = true, dataType = "ProjectType")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void createmProjectType(@RequestHeader(value = "schemaID", required = true) String schemaId,
			@RequestBody ProjectTypeVo protVo) throws JsonProcessingException {
		projectTypeService.CreatemProjectType(protVo);
	}
	
	@ApiOperation(value = "编辑项目分类", notes = "编辑项目分类")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public void updateProjectType(@RequestBody ProjectType projectType) {
		projectTypeService.updateProjectType(projectType);
	}

	@ApiOperation(value = "项目分类列表", notes = "项目分类列表")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public PageEntity<ProjectType> queryMember(@RequestHeader(value = "schemaID", required = true) String schemaID,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum) {
		return this.projectTypeService.proTypeList(curPage, perPageSum);
	}

	@ApiOperation(value = "获取项目分类详情", notes = "获取项目分类详情")
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public void detailProjectType(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = "schemaID", required = false) String schemaID) {
		this.projectTypeService.proTypeDetail(id);
	}

	@ApiOperation(value = "删除项目分类", notes = "删除项目分类")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deletePro(Integer id) {
		projectTypeService.deleteType(id);
	}

}