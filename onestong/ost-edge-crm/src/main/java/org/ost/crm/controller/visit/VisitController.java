package org.ost.crm.controller.visit;

import java.util.List;
import java.util.Map;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.model.visit.dto.CreateVisitDto;
import org.ost.crm.model.visit.dto.UpdateVisitDto;
import org.ost.crm.model.visit.dto.VisitAttenceCreateDto;
import org.ost.crm.model.visit.dto.VisitAttenceDto;
import org.ost.crm.model.visit.dto.VisitDetailDto;
import org.ost.crm.services.visit.VisitAttenceService;
import org.ost.crm.services.visit.VisitService;
import org.ost.entity.notification.visit.UnSignOutVisit;
import org.ost.entity.user.Users;
import org.ost.entity.user.dto.UserListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;

@RestController
public class VisitController extends Action {
	@Autowired
	private VisitService visitService;
	@Autowired
	private VisitAttenceService visitAttenceService;

	@GetMapping(value = "visitEvent/noSignOut")
	public OperateResult<List<UnSignOutVisit>> queryUnSignOut() {
		return new OperateResult<List<UnSignOutVisit>>(visitAttenceService.queryNoSignOut());
	}

	/**
	 * 新增外访
	 * 
	 * @throws JsonProcessingException
	 */
	@ApiOperation(value = "新增外访", notes = "", code = 200, produces = "application/json")
	@PostMapping(value = "visitEvent")
	public OperateResult<String> createVisit(@RequestAttribute(value = LOGIN_USER) Users currentUser,
			@RequestBody CreateVisitDto createVisitDto) throws JsonProcessingException {
		return new OperateResult<String>(visitService.createVisit(currentUser, createVisitDto));
	}

	/**
	 * 外访表单参数 ✅
	 * 
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "外访表单参数", notes = "", code = 200, produces = "application/json")
	@GetMapping(value = "visitEvent/params")
	public OperateResult<Map<String, List>> queryAttrs(@RequestAttribute(value = LOGIN_USER) Users currentUser)
			throws JsonProcessingException {
		return new OperateResult<Map<String, List>>(visitService.queryVisitAttrs());
	}

	/**
	 * ✅
	 * 
	 * @param currentUser
	 * @param visitId
	 * @param approvalStatus
	 * @return
	 */
	@ApiOperation(value = "外访审批", notes = "", code = 200, produces = "application/json")
	@PostMapping(value = "visitEvent/{id}/approval")
	public OperateResult<String> approval(@RequestAttribute(value = LOGIN_USER) Users currentUser,
			@PathVariable("id") Integer visitId,
			@RequestParam(value = "approvalStatus", required = true) Byte approvalStatus) {
		return new OperateResult<String>(visitService.doApproval(currentUser, visitId, approvalStatus));
	}

	/**
	 * ✅
	 * 
	 * @param currentUser
	 * @param visitAttenceCreateDto
	 * @param visitId
	 * @return
	 */
	@ApiOperation(value = "外访签到", notes = "", code = 200, produces = "application/json")
	@PostMapping(value = "visitEvent/{id}/signin")
	public OperateResult<String> signIn(@RequestAttribute(value = LOGIN_USER) Users currentUser,
			@RequestBody VisitAttenceCreateDto visitAttenceCreateDto, @PathVariable("id") Integer visitId) {
		return new OperateResult<String>(visitAttenceService.signIn(currentUser, visitId, visitAttenceCreateDto));
	}

	/**
	 * ✅
	 * 
	 * @param currentUser
	 * @param visitAttenceCreateDto
	 * @param visitId
	 * @return
	 */
	@ApiOperation(value = "外访签退", notes = "", code = 200, produces = "application/json")
	@PostMapping(value = "visitEvent/{id}/signout")
	public OperateResult<String> signOut(@RequestAttribute(value = LOGIN_USER) Users currentUser,
			@RequestBody VisitAttenceCreateDto visitAttenceCreateDto, @PathVariable("id") Integer visitId) {
		return new OperateResult<String>(visitAttenceService.signOut(currentUser, visitId, visitAttenceCreateDto));
	}

	/**
	 * ✅
	 * 
	 * @param currentUser
	 * @param id
	 * @param actionType
	 * @return
	 */
	@ApiOperation(value = "外访详细", notes = "", code = 200, produces = "application/json", response = VisitDetailDto.class)
	@GetMapping(value = "visitEvent/{id}")
	public OperateResult<VisitDetailDto> detail(@RequestAttribute(value = LOGIN_USER) Users currentUser,
			@PathVariable(value = "id") Integer id, @RequestParam(value = "actionType") String actionType) {
		return new OperateResult<VisitDetailDto>(visitService.queryDetail(currentUser, id, actionType));
	}

	/**
	 * 
	 * @param currentUser
	 * @param id
	 * @param actionType
	 * @return
	 * @throws JsonProcessingException
	 */
	@ApiOperation(value = "修改外访", notes = "", code = 200, produces = "application/json")
	@PutMapping(value = "visitEvent/{id}")
	public OperateResult<String> update(@RequestAttribute(value = LOGIN_USER) Users currentUser,
			@PathVariable(value = "id") Integer id, @RequestBody UpdateVisitDto updateVisitDto)
			throws JsonProcessingException {
		return new OperateResult<String>(visitService.updateVisit(currentUser, updateVisitDto, id));
	}

	/**
	 * ✅
	 * 
	 * @param currentUser
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "外访支持人员列表", notes = "", code = 200, produces = "application/json", response = UserListDto.class)
	@GetMapping(value = "visitEvent/{id}/user")
	public OperateResult<Map<String, Object>> querySupporters(@RequestAttribute(value = LOGIN_USER) Users currentUser,
			@PathVariable(value = "id") Integer id) {
		return new OperateResult<Map<String, Object>>(visitService.querySupporters(currentUser, id));
	}

	/**
	 * ✅
	 * 
	 * @param currentUser
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取考勤记录", notes = "", code = 200, produces = "application/json", response = VisitAttenceDto.class)
	@GetMapping(value = "visitEvent/{id}/attendance")
	public OperateResult<VisitAttenceDto> queryAttence(@RequestAttribute(value = LOGIN_USER) Users currentUser,
			@PathVariable(value = "id") Integer id) {
		return new OperateResult<VisitAttenceDto>(visitAttenceService.queryAttence(currentUser, id));
	}
}
