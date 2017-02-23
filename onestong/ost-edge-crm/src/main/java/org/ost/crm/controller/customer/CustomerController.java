package org.ost.crm.controller.customer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.common.tools.db.Page;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.customer.CustomerService;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("customer")
public class CustomerController extends Action {
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Object createCustomer(Users current,@RequestBody Map<String, Object> params) throws JsonProcessingException {
		return this.customerService.createCustomer(currentUser(), params);

	}

	
	
	
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Object queryCustomers(
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			Users current, @RequestParam(value = "belongIndustry", required = false) String belongIndustry,
			@RequestParam(value = "dealFrequency", required = false) String dealFrequency,
			@RequestParam(value = "isPlc", required = false) String isPlc,
			@RequestParam(value = "natrue", required = false) String natrue,
			@RequestParam(value = "turnover", required = false) String turnover,
			@RequestParam(value = "type", required = false) String type)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(belongIndustry))
			params.put("belongIndustry", belongIndustry);
		if (StringUtils.isNotEmpty(dealFrequency))
			params.put("dealFrequency", dealFrequency);
		if (StringUtils.isNotEmpty(isPlc))
			params.put("isPlc", isPlc);
		if (StringUtils.isNotEmpty(natrue))
			params.put("natrue", natrue);
		if (StringUtils.isNotEmpty(turnover))
			params.put("turnover", turnover);
		if (StringUtils.isNotEmpty(type))
			params.put("type", type);
		Page page  = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		return this.customerService.queryCustomers(current, params, page);
	}

}
