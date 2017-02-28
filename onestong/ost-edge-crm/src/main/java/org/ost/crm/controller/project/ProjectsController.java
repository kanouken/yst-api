package org.ost.crm.controller.project;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.dao.project.ProjectPaymentDao;
import org.ost.crm.services.project.ProjectService;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.project.ProjectPayment;
import org.ost.entity.project.dto.ProjectCreateOrUpdateDto;
import org.ost.entity.project.dto.ProjectPaymentDto;
import org.ost.entity.project.dto.ProjectStepsDetailDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("project")
public class ProjectsController extends Action {

	@Autowired
	private ProjectService projectService;

	@PostMapping(value = "")
	private OperateResult<String> createProject(@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestBody ProjectCreateOrUpdateDto dto) {

		return new OperateResult<String>(projectService.createProject(user, dto));
	}

	@PutMapping(value = "")
	private OperateResult<String> updateProject(@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestBody ProjectCreateOrUpdateDto dto) {

		return new OperateResult<String>(projectService.updateProject(user, dto));
	}

	@DeleteMapping(value = "{id}")
	private OperateResult<String> deleteProject(@PathVariable(value = "id") Integer projectId,
			@RequestAttribute(value = LOGIN_USER) Users user) {
		return new OperateResult<String>(projectService.deleteProject(projectId, user));
	}

	/**
	 * 更新 ／ 新增回款流程
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

	@PutMapping(value = "steps/{projectId}")
	public void updateProjectSteps(@PathVariable(value = "projectId") Integer projectId,
			@RequestAttribute(value = LOGIN_USER) Users users, @RequestBody List<ContactsListDto> dtos) {

	}

	/**
	 * 项目类型数据
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
}
