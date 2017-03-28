package org.ost.crm.controller.visit;

import java.util.List;
import java.util.Map;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.model.visit.dto.CreateVisitDto;
import org.ost.crm.model.visit.dto.VisitAttenceCreateDto;
import org.ost.crm.services.visit.VisitAttenceService;
import org.ost.crm.services.visit.VisitService;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	 * 外访表单参数
	 * ✅
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

}
