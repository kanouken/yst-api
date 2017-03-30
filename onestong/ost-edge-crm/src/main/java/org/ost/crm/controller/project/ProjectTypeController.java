package org.ost.crm.controller.project;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.project.ProjectTypeService;

import org.ost.entity.project.vo.ProjectTypeVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "projectType")
public class ProjectTypeController extends Action {
	@Autowired
	public ProjectTypeService projectTypeService;

	@ApiOperation(value = "新增项目分类", notes = "新增项目分类")
	@PostMapping(value = "")
	public OperateResult<String> createProjectType(
			@RequestBody ProjectTypeVo projectTypeVo,
			@RequestAttribute(value = LOGIN_USER) Users user) throws JsonProcessingException {
		return new OperateResult<String>(projectTypeService.createProjectType(user, projectTypeVo));
	}

	@ApiOperation(value = "编辑项目分类", notes = "编辑项目分类")
	@PutMapping(value = "{id}")
	public OperateResult<String> updateProjectType(
			@PathVariable(value = "id") Integer id,
			@RequestBody ProjectTypeVo projectTypeVo, @RequestAttribute(value = LOGIN_USER) Users user)
					throws JsonProcessingException {
		return new OperateResult<String>(projectTypeService.updateProjectType(id, user, projectTypeVo));
	}

	@ApiOperation(value = "项目分类列表", notes = "项目分类列表")
	@GetMapping(value = "list")
	public OperateResult<Map<String, Object>> queryMember(
			@RequestAttribute(value = LOGIN_USER, required = false) Users user,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestParam(value = "name", required = false) String names) {
		return new OperateResult<Map<String, Object>>(
				this.projectTypeService.queryMember(names, user, curPage, perPageSum));
	}

	@ApiOperation(value = "获取项目分类详情", notes = "获取项目分类详情")
	@GetMapping(value = "{id}")
	public ProjectTypeVo detailProjectType(
			@PathVariable Integer id,
			@RequestAttribute(value = LOGIN_USER, required = false) Users user) {
		return this.projectTypeService.detailProjectType(id, user);
	}

	@ApiOperation(value = "删除项目分类", notes = "删除项目分类")
	@DeleteMapping(value = "{id}")
	public OperateResult<String> deleteProjectType(
			@PathVariable Integer id,
			@RequestAttribute(value = LOGIN_USER) Users user) {
		return new OperateResult<String>(projectTypeService.deleteProjectType(id, user));
	}

}