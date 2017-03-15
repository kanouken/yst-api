package org.ost.crm.controller.report;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.entity.customer.vo.CustomerRepot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("report")
public class CustomerReportController {
	@Autowired
	private org.ost.crm.services.report.CustomerResportService customerResportService;
	
	@ApiOperation(value = "客户报表列表", notes = "客户报表列表")
	@GetMapping(value = "/paramlist")
	public OperateResult<List<CustomerRepot>> queryCustomerByParam(@RequestHeader(value = "userID") Integer userID,
			@RequestBody CustomerRepot cus) throws JsonProcessingException{
		return new OperateResult<List<CustomerRepot>>(customerResportService.reportCustomer(userID, cus));
	}
	
}
