package org.ost.crm.controller.project;

import java.util.Map;

import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.project.ProjectFileService;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Api(tags = "项目文件")
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
}
