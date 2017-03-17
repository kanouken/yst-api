package org.ost.crm.controller.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.report.KeHuReportService;
import org.ost.entity.report.dto.KeHuReportDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("report/cKeHuReport")
public class KeHuReportController extends Action {
	@Autowired
	private KeHuReportService keHuReportService;

	@ApiOperation(value = "客户报表列表", notes = "客户报表列表")
	@GetMapping(value = "/list")
	public OperateResult<Map<String, Object>> queryCustomersReport(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,			
			@RequestParam(value = "dealFrequency", required = false) String dealFrequency,
			@RequestParam(value = "turnover", required = false) String turnover,
			@RequestParam(value = "isPlc", required = false) String isPlc,
			@RequestParam(value = "nature", required = false) String nature,
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "belongIndustry", required = false) String belongIndustry,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(belongIndustry))
			params.put("belongIndustry", belongIndustry);
		if (StringUtils.isNotEmpty(dealFrequency))
			params.put("dealFrequency", dealFrequency);
		if (StringUtils.isNotEmpty(isPlc))
			params.put("isPlc", isPlc);
		if (StringUtils.isNotEmpty(managerOwnerName))
			params.put("managerOwnerName", managerOwnerName);
		if (StringUtils.isNotEmpty(turnover))
			params.put("turnover", turnover);
		if (StringUtils.isNotEmpty(source))
			params.put("source", source);
		if (StringUtils.isNotEmpty(turnover))
			params.put("turnover", turnover);
		if (StringUtils.isNotEmpty(type))
			params.put("type", type);
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		return new OperateResult<Map<String, Object>>(this.keHuReportService.reportCustomer(managerOwnerName, params, page));
	}
	
}
