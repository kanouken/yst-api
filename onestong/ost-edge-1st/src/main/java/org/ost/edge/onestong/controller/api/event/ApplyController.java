package org.ost.edge.onestong.controller.api.event;

import org.common.tools.OperateResult;
import org.ost.edge.onestong.controller.base.Action;
import org.ost.edge.onestong.model.user.User;
import org.ost.edge.onestong.services.api.event.ApprovalService;
import org.ost.edge.onestong.services.api.event.ApprovalService.ApprovalState;
import org.ost.entity.event.approval.dto.ApprovalEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "approvalEvent")
public class ApplyController extends Action {
	@Autowired
	private ApprovalService approvalService;

	/**
	 * 
	 * 新增审批
	 */
	@PostMapping(value = "")
	public OperateResult<ApprovalEventDto> createApply(@RequestAttribute(value = LOGIN_USER) User users,
			@RequestBody ApprovalEventDto dto) {
		return new OperateResult<ApprovalEventDto>(approvalService.createApprovalEvent(users, dto));
	}

	/**
	 * 申请审批
	 */
	@PutMapping(value = "approval")
	public OperateResult<String> approval(@RequestAttribute(value = LOGIN_USER) User users,
			@RequestParam(value = "action") String action, @RequestParam(value = "eId") String eventId) {
		return new OperateResult<String>(approvalService.approval(users, action, eventId));
	}

	@GetMapping(value = "detail")
	public OperateResult<ApprovalEventDto> deleteApply(@RequestAttribute(value = LOGIN_USER) User user, @RequestParam(value = "eId") String eId) {
		
		return new OperateResult<ApprovalEventDto>(approvalService.queryDetail(user,eId));
	}

}
