package org.ost.crm.controller.report;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 联系活动报表 (外访事件报表)
 * 
 * @author xnq
 *
 */
@RestController
@RequestMapping(value = "report/cLianXiHuoDongReport")
public class VisitReportController extends Action {

	@Autowired
	private VisitReportService visitReportService;

	/**
	 * 联系活动报表- 获取列表数据 contactType 联系类型（电话，拜访） string @mock= endDate 结束时间
	 * string @mock=2016/01/31 hasBus 是否有商机（0，1） string @mock= managerOwnerName
	 * 客户经理 string @mock=姓名 startDate
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "list")
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
	@GetMapping(value = "count")
	public OperateResult<Map<String, Object>> querySummary(@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "hasBus", required = false) String hasBus,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "contactType", required = false) String contactType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) throws Exception {

		return new OperateResult<Map<String, Object>>(
				visitReportService.querySummary(user, hasBus, managerOwnerName, contactType, startDate, endDate));
	}

	@GetMapping(value = "chart")
	public OperateResult<List<Map<String, Object>>> queryChart(@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestParam(value = "hasBus", required = false) String hasBus,
			@RequestParam(value = "managerOwnerName", required = false) String managerOwnerName,
			@RequestParam(value = "contactType", required = false) String contactType) {
		return new OperateResult<List<Map<String, Object>>>(
				visitReportService.queryChart(user, hasBus, managerOwnerName, contactType));
	}

}
