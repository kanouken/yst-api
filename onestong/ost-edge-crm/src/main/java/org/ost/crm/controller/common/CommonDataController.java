package org.ost.crm.controller.common;

import java.util.Map;

import org.common.tools.OperateResult;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.base.BaseService;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "commonData")
public class CommonDataController extends Action {

	@Autowired
	private BaseService baseService;

	@GetMapping(value = "cities")
	public OperateResult<Map<String, Object>> cities(@RequestAttribute(value = LOGIN_USER) Users user) {
		return new OperateResult<Map<String, Object>>(baseService.getParamJson("city_data", "0"));
	}
}
