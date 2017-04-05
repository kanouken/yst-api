package org.ost.crm.controller.report;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.report.VisitReportService;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 联系活动报表 (外访事件报表)
 * 
 * @author xnq
 *
 */
@RestController
public class VisitReportController extends Action {

	@Autowired
	private VisitReportService visitReportService;

	@ApiOperation(value = "外访报表导出", notes = "")
	@RequestMapping(value = "report/visit/export", method = RequestMethod.GET)
	public void exportVisit(@RequestParam(value = "departmentId", required = true) Integer departmentId,
			@RequestParam(value = "startTime", required = true) String startTime,
			@RequestParam(value = "endTime", required = true) String endTime, HttpServletResponse response)
			throws IOException, Exception {
		this.responseWriteFile(response, visitReportService.exportVisit(departmentId, startTime, endTime),
				"外访记录报表.xls");
	}

	/**
	 * 联系活动报表- 获取列表数据 contactType 联系类型（电话，拜访） string @mock= endDate 结束时间
	 * string @mock=2016/01/31 hasBus 是否有商机（0，1） string @mock= managerOwnerName
	 * 客户经理 string @mock=姓名 startDate
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "report/cLianXiHuoDongReport/list")
	public OperateResult<Map<String, Object>> queryList(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "hasBus", required = false) String hasBus,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "contactType", required = false) String contactType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) throws Exception {

		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		return new OperateResult<Map<String, Object>>(
				visitReportService.queryList(user, page, hasBus, managerOwnerName, contactType, startDate, endDate));
	}

	/**
	 * 联系活动报表- 获取列表数据 contactType 联系类型（电话，拜访） string @mock= endDate 结束时间
	 * string @mock=2016/01/31 hasBus 是否有商机（0，1） string @mock= managerOwnerName
	 * 客户经理 string @mock=姓名 startDate
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "report/cLianXiHuoDongReport/count")
	public OperateResult<Map<String, Object>> querySummary(@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "hasBus", required = false) String hasBus,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "contactType", required = false) String contactType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) throws Exception {

		return new OperateResult<Map<String, Object>>(
				visitReportService.querySummary(user, hasBus, managerOwnerName, contactType, startDate, endDate));
	}

	@GetMapping(value = "report/cLianXiHuoDongReport/chart")
	public OperateResult<List<Map<String, Object>>> queryChart(@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "hasBus", required = false) String hasBus,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "contactType", required = false) String contactType) {
		return new OperateResult<List<Map<String, Object>>>(
				visitReportService.queryChart(user, hasBus, managerOwnerName, contactType));
	}

	/**
	 * 联系活动报表- 导出列表数据 contactType 联系类型（电话，拜访） string @mock= endDate 结束时间
	 * string @mock=2016/01/31 hasBus 是否有商机（0，1） string @mock= managerOwnerName
	 * 客户经理 string @mock=姓名 startDate
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "report/cLianXiHuoDongReport/export")
	public void export(@RequestParam(value = "schemaID", required = false) String schemaID,
			@RequestParam(value = "hasBus", required = false) String hasBus,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "contactType", required = false) String contactType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.responseWriteFile(response, visitReportService.export(schemaID, hasBus, managerOwnerName, contactType,
				startDate, endDate, 1, 100000), "联系活动报表.xlsx");
	}

}
