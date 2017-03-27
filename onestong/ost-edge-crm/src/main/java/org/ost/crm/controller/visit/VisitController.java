package org.ost.crm.controller.visit;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.model.visit.dto.CreateVisitDto;
import org.ost.crm.services.visit.VisitService;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;

@RestController
public class VisitController extends Action {
	@Autowired
	private VisitService visitService;

	/**
	 * 新增外访
	 * @throws JsonProcessingException 
	 */
	@ApiOperation(value = "新增外访", notes = "", code = 200, produces = "application/json")
	@PostMapping(value = "visitEvent")
	public OperateResult<String> createVisit(@RequestAttribute(value = LOGIN_USER) Users currentUser,
			@RequestBody CreateVisitDto createVisitDto) throws JsonProcessingException {
		return new OperateResult<String>(visitService.createVisit(currentUser,createVisitDto));
	}

}
