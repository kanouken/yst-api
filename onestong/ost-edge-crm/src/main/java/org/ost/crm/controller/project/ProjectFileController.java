package org.ost.crm.controller.project;

import java.util.List;
import java.util.Map;

import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.project.ProjectFileService;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
// @Api(tags = "项目文件")
@RequestMapping("projectFile")
public class ProjectFileController extends Action {

	@Autowired
	private ProjectFileService projectFileService;

	@PostMapping(value = "project/{id}/file")
	public OperateResult<String> uploadFile(@RequestAttribute(value = LOGIN_USER) Users user,
			@PathVariable(value = "id") Integer projectId, @RequestParam(value = "fid") String fid,
			@RequestParam(value = "name") String name) {
		return new OperateResult<String>(projectFileService.saveProjectFile(user, fid, name, projectId));
	}

	@GetMapping(value = "project/{id}/file/list")
	public OperateResult<Map<String, Object>> queryProjectFiles(@PathVariable(value = "id") Integer projectId,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum) {
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		return new OperateResult<Map<String, Object>>(projectFileService.queryProjectFiles(user, page, projectId));
	}

	@ApiOperation(value = "批量逻辑删除", notes = "批量逻辑删除")
	@DeleteMapping(value = "project/{id}/file")
	public OperateResult<String> deleteFile(
			@RequestAttribute(value = LOGIN_USER) Users user,
			@PathVariable(value = "id") Integer projectId,
			@RequestParam(value = "ids") String fids) {
		return new OperateResult<String>(this.projectFileService.deleteFile(user,projectId,fids));
	}

	@ApiOperation(value = "回收站列表", notes = "回收站列表")
	@GetMapping(value = "project/file/list")
	public OperateResult<Map<String, Object>> queryDeleteProjectFiles(
			@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "updateTime", required = false) String updateTime,
			@RequestParam(value = "updateBy", required = false) String updateBy
			){
		return new OperateResult<Map<String, Object>>(this.projectFileService.queryDeleteProjectFiles(user, curPage, perPageSum, name, updateTime, updateBy));
	}

	@ApiOperation(value = "彻底删除", notes = "彻底删除")
	@DeleteMapping(value = "project/file/delete")
	public OperateResult<String> deleteProjectFiles(
			@RequestAttribute(value = LOGIN_USER) Users user,
			@PathVariable(value = "id") Integer id
			){
		return new OperateResult<String>(this.projectFileService.deleteProjectFiles(user, id));
	}
	
	@ApiOperation(value = "还原", notes = "还原")
	@PutMapping(value = "project/file/update")
	public OperateResult<String> updateProjectFiles(
			@RequestAttribute(value = LOGIN_USER) Users user,
			@PathVariable(value = "id") Integer id
			){
		return new OperateResult<String>(this.projectFileService.updateProjectFiles(user, id));
	}
	
	@ApiOperation(value = "全部还原", notes = "全部还原")
	@PutMapping(value = "project/file/updateAll")
	public OperateResult<String> updateAllProjectFiles(
			@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "ids") String fids) {
		return new OperateResult<String>(this.projectFileService.updateAllProjectFiles(user, fids));
	}
	
	@ApiOperation(value = "全部清空", notes = "全部清空")
	@DeleteMapping(value = "project/file/deleteAll")
	public OperateResult<String> deleteAllProjectFiles(
			@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "ids") String fids
			){
		return new OperateResult<String>(this.projectFileService.deleteAllProjectFiles(user, fids));
	}
}
