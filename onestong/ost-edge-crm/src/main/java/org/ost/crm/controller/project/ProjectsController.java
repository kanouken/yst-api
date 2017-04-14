package org.ost.crm.controller.project;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.project.ProjectService;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.project.Project;
import org.ost.entity.project.dto.ProjectCreateOrUpdateDto;
import org.ost.entity.project.dto.ProjectDetailDto;
import org.ost.entity.project.dto.ProjectPaymentDto;
import org.ost.entity.project.dto.ProjectStepsDetailDto;
import org.ost.entity.project.dto.ProjectStepsDto;
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

@RestController
@RequestMapping("project")
public class ProjectsController extends Action {

	@Autowired
	private ProjectService projectService;

	/**
	 * 项目详细 ✅
	 * 
	 * @param projectId
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@GetMapping(value = "{id}")
	public OperateResult<ProjectDetailDto> queryDetail(@PathVariable(value = "id") Integer projectId,
			@RequestAttribute(value = LOGIN_USER) Users user) throws InterruptedException, ExecutionException {
		return new OperateResult<ProjectDetailDto>(projectService.queryDetail(projectId, user));
	}

	/**
	 * 项目列表 FIXME YSTCRM-280 3. 项目列表 1. 普通员工-项目列表只能显示归属自己的项目。 2.
	 * 部门主管可以查看本部门所有项目，以及下级部门所有项目。
	 * 
	 * @param projectId
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@GetMapping(value = "list")
	public OperateResult<Map<String, Object>> queryProjects(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "customerID", required = false) Integer customerId,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "typeID", required = false) String typeId,
			@RequestParam(value = "visitEventID", required = false) String visitEventID)
			throws InterruptedException, ExecutionException {
		return new OperateResult<Map<String, Object>>(projectService.queryProjectsUserScoped(visitEventID, user, customerId,
				keyword, name, state, typeId, curPage, perPageSum));
	}

	/**
	 * 新增项目 ✅
	 * 
	 * @param user
	 * @param dto
	 * @return
	 */
	@PostMapping(value = "")
	public OperateResult<String> createProject(@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestBody ProjectCreateOrUpdateDto dto) {

		return new OperateResult<String>(projectService.createProject(user, dto));
	}

	/**
	 * 更新项目
	 * 
	 * @param user
	 * @param dto
	 * @return
	 */
	@PutMapping(value = "{id}")
	public OperateResult<String> updateProject(@RequestAttribute(value = LOGIN_USER) Users user,
			@PathVariable(value = "id") Integer id, @RequestBody ProjectCreateOrUpdateDto dto) {
		return new OperateResult<String>(projectService.updateProject(user, id, dto));
	}

	/**
	 * 删除项目
	 * 
	 * @param projectId
	 * @param user
	 * @return
	 */
	@DeleteMapping(value = "{id}")
	public OperateResult<String> deleteProject(@PathVariable(value = "id") Integer projectId,
			@RequestAttribute(value = LOGIN_USER) Users user) {
		return new OperateResult<String>(projectService.deleteProject(projectId, user));
	}

	/**
	 * 更新 ／ 新增回款流程 ✅
	 * 
	 * @param projectId
	 * @param users
	 * @param dtos
	 * @return
	 */
	@PutMapping(value = "payment/{projectId}")
	public OperateResult<String> updateProjectPayment(@PathVariable(value = "projectId") Integer projectId,
			@RequestAttribute(value = LOGIN_USER) Users users, @RequestBody List<ProjectPaymentDto> dtos) {
		return new OperateResult<String>(projectService.updateProjectPayment(users, projectId, dtos));
	}

	/**
	 * 
	 * 更新联系人 ✅
	 * 
	 * @param projectId
	 * @param users
	 * @param dtos
	 * @return
	 */
	@PutMapping(value = "contact/{projectId}")
	public OperateResult<String> updateProjectContacts(@PathVariable(value = "projectId") Integer projectId,
			@RequestAttribute(value = LOGIN_USER) Users users, @RequestBody List<ContactsListDto> dtos) {
		return new OperateResult<String>(projectService.updateProjectContacts(users, projectId, dtos));
	}

	/**
	 * 更新项目阶段 ✅
	 * 
	 * @param projectId
	 * @param users
	 * @param dtos
	 */
	@PutMapping(value = "steps/{projectId}")
	public OperateResult<String> updateProjectSteps(@PathVariable(value = "projectId") Integer projectId,
			@RequestAttribute(value = LOGIN_USER) Users users, @RequestBody List<ProjectStepsDto> dtos) {
		return new OperateResult<String>(projectService.updateProjectSteps(projectId, users, dtos));
	}

	/**
	 * 获取项目阶段 ✅
	 * 
	 * @param typeID
	 * @param user
	 * @return
	 */
	@GetMapping(value = "stage/{typeID}")
	public OperateResult<ProjectStepsDetailDto> queryProjectSteps(@PathVariable(value = "typeID") Integer typeID,
			@RequestAttribute(value = LOGIN_USER) Users user) {
		return new OperateResult<ProjectStepsDetailDto>(this.projectService.queryProjectSteps(typeID, user));
	}

	/**
	 * 获取项目参数 ✅
	 * 
	 * @param users
	 * @return
	 */
	@GetMapping(value = "params")
	public OperateResult<Map<String, Object>> queryProjectParams(@RequestAttribute(value = LOGIN_USER) Users users) {
		return new OperateResult<Map<String, Object>>(projectService.queryProjectParams(users));
	}

	@PutMapping(value = "{id}/state")
	public OperateResult<String> updateProjectState(@RequestAttribute(value = LOGIN_USER) Users user,
			@PathVariable(value = "id") Integer projectId, @RequestParam(value = "state") String state) {
		return new OperateResult<String>(projectService.updateProjectSteps(user, projectId, state));
	}

}
