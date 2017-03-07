package org.ost.crm.controller.project;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.project.ProjectTypeService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.project.ProjectType;

import org.ost.entity.project.vo.ProjectTypeVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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
	public OperateResult<String> createmProjectType(@RequestBody ProjectTypeVo proTVo, @RequestAttribute(value = LOGIN_USER) Users user)
			throws JsonProcessingException {
		return new OperateResult<String>(projectTypeService.createmProjectType(user, proTVo));
	}

	@ApiOperation(value = "编辑项目分类", notes = "编辑项目分类")
	@PutMapping(value = "{id}")
	public OperateResult<String> updateProjectType(@PathVariable(value = "id") Integer id, @RequestBody ProjectTypeVo proTVo,
			@RequestAttribute(value = LOGIN_USER) Users user) throws JsonProcessingException {
		return new OperateResult<String>(projectTypeService.updateProjectType(id, user, proTVo));
	}

	@ApiOperation(value = "项目分类列表", notes = "项目分类列表")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public PageEntity<ProjectType> queryMember(@RequestHeader(value = LOGIN_USER) String schemaId,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum) {
		return this.projectTypeService.proTypeList(schemaId, curPage, perPageSum);
	}

	@ApiOperation(value = "获取项目分类详情", notes = "获取项目分类详情")
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public void detailProjectType(@PathVariable Integer id,
			@RequestHeader(value = LOGIN_USER) String schemaId) {
		this.projectTypeService.proTypeDetail(id,schemaId);
	}

	@ApiOperation(value = "删除项目分类", notes = "删除项目分类")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public OperateResult<String> deletePro(@PathVariable Integer id, @RequestHeader(value = LOGIN_USER) String schemaId) {
		return new OperateResult<String>(projectTypeService.deleteType(id, schemaId));
	}

}