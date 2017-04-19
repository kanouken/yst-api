package org.ost.crm.controller.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ost.crm.controller.base.Action;
import org.ost.crm.services.report.ApprovalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("report/approvalReport")
public class ApprovalReportController extends Action {

	@Autowired
	private ApprovalReportService approvalReportService;

	@ApiOperation(value = "请假_出差", notes = "请假_出差")
	@GetMapping(value = "export")
	public void approvalExport(
			@RequestParam(value = "deptId", required = false) Integer deptId,
			@RequestParam(value = "signedTime", required = false) String signedTime,
			@RequestParam(value = "signoutTime", required = false) String signoutTime, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.responseWriteFile(
				response,
				approvalReportService.approvalExport(
						deptId, 
						signedTime, 
						signoutTime, 
						1, 
						100000),
				"请假_出差.xlsx"
				);
	}
}
