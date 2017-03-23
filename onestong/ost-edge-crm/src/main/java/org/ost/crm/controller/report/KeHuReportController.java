package org.ost.crm.controller.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.report.KeHuReportService;
import org.ost.entity.report.dto.KeHuReportDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@ApiOperation(value = "客户报表-获取列表数据", notes = "客户报表-获取列表数据")
	@GetMapping(value = "/list")
	public OperateResult<Map<String, Object>> queryCustomersReport(
			@RequestAttribute(value = LOGIN_USER) Users users,
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
			@RequestParam(value = "endDate", required = false) String endDate, 
			HttpServletRequest request)
					throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> params = this.getRequestParam(request);

		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		return new OperateResult<Map<String, Object>>(
				this.keHuReportService.queryCustomersReport(params, page, users));
	}

	@ApiOperation(value = "客户报表-获取图表数据", notes = "客户报表-获取图表数据")
	@GetMapping(value = "chart")
	public Object reportChart(@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestAttribute(value = LOGIN_USER) Users users,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "belongIndustry", required = false) String belongIndustry,
			@RequestParam(value = "dealFrequency", required = false) String dealFrequency,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "isPlc", required = false) String isPlc,
			@RequestParam(value = "nature", required = false) String nature,
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "turnover", required = false) String turnover, HttpServletRequest request)
					throws InterruptedException, ExecutionException {
		Map<String, Object> params = this.getRequestParam(request);
		
		return keHuReportService.reportChart(params, users);
	}

	@ApiOperation(value = "客户报表-获取统计数据", notes = "客户报表-获取统计数据")
	@GetMapping(value = "count")
	public Object reportCount(@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestAttribute(value = LOGIN_USER) Users users,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "belongIndustry", required = false) String belongIndustry,
			@RequestParam(value = "dealFrequency", required = false) String dealFrequency,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "isPlc", required = false) String isPlc,
			@RequestParam(value = "nature", required = false) String nature,
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "turnover", required = false) String turnover, HttpServletRequest request
			) {
		Map<String, Object> params = this.getRequestParam(request);
		return keHuReportService.reportCount(params, users);
	}

}
