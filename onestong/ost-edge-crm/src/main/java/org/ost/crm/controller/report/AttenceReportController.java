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
	
	@ApiOperation(value = "考勤报表", notes = "考勤报表")
	@GetMapping(value = "export")
	public void attenceExport(
			@RequestParam(value = "departmentName", required = false) String departmentName,
			@RequestParam(value = "signedTime", required = false) String signedTime,
			@RequestParam(value = "signoutTime", required = false) String signoutTime,
			HttpServletRequest request,
			HttpServletResponse response
			)throws Exception {
        this.responseWriteFile(
                response,
                attenceReportService.attenceExport(
                		departmentName,
                		signedTime,
                		signoutTime,
                		1,
                		100000),
                "考勤报表.xlsx"
        );
	}
}
