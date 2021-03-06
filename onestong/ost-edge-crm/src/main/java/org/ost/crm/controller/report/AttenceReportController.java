package org.ost.crm.controller.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ost.crm.controller.base.Action;
import org.ost.crm.services.report.AttenceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("report/attenceReport")
public class AttenceReportController extends Action{
	
	@Autowired
	private AttenceReportService attenceReportService;
	
	@ApiOperation(value = "考勤", notes = "考勤")
	@GetMapping(value = "export")
	public void attenceExport(
			@RequestParam(value = "deptId") Integer deptId,
			@RequestParam(value = "signedTime", required = false) String signedTime,
			@RequestParam(value = "signoutTime", required = false) String signoutTime,
			HttpServletRequest request,
			HttpServletResponse response
			)throws Exception {
        this.responseWriteFile(
                response,
                attenceReportService.attenceExport(
                		deptId,
                		signedTime,
                		signoutTime,
                		1,
                		100000),
                "考勤.xlsx"
        );
	}
}
