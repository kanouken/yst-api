package org.ost.crm.controller.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.report.CustomerResportService;
import org.ost.entity.customer.vo.CustomerRepot;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("report")
public class CustomerReportController extends Action {
	@Autowired
	private  CustomerResportService reprotService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public OperateResult<Map<String, Object>> queryCustomers(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestAttribute(value = LOGIN_USER) Users current,
			@RequestParam(value = "belongIndustry", required = false) String belongIndustry,
			@RequestParam(value = "dealFrequency", required = false) String dealFrequency,
			@RequestParam(value = "isPlc", required = false) String isPlc,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "natrue", required = false) String natrue,
			@RequestParam(value = "turnover", required = false) String turnover,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "a", required = false) String a,
			@RequestParam(value = "b", required = false) String b,
			@RequestParam(value = "c", required = false) String c

	) throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(belongIndustry))
			params.put("belongIndustry", belongIndustry);
		if (StringUtils.isNotEmpty(dealFrequency))
			params.put("dealFrequency", dealFrequency);
		if (StringUtils.isNotEmpty(isPlc))
			params.put("isPlc", isPlc);
		if (StringUtils.isNotEmpty(keyword))
			params.put("keyword", keyword);
		if (StringUtils.isNotEmpty(name))
			params.put("name", name);
		if (StringUtils.isNotEmpty(natrue))
			params.put("nature", natrue);
		if (StringUtils.isNotEmpty(turnover))
			params.put("turnover", turnover);
		if (StringUtils.isNotEmpty(type))
			params.put("type", type);
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		return new OperateResult<Map<String, Object>>(this.reprotService.queryCustomers(current, params, page));
	}

}
