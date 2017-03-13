package org.ost.crm.controller.customer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.controller.base.Action;
import org.ost.crm.services.customer.CustomerService;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

@RestController
@RequestMapping("customer")
public class CustomerController extends Action {
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public OperateResult<String> createCustomer(@RequestBody Map<String, Object> params,
			@RequestAttribute(value = LOGIN_USER) Users user) throws JsonProcessingException {
		return new OperateResult<String>(this.customerService.createCustomer(user, params));

	}

	@PutMapping(value = "{customerId}")
	public OperateResult<String> updateCustomer(@PathVariable(value = "customerId") Integer customerId,
			@RequestBody Map<String, Object> params, @RequestAttribute(value = LOGIN_USER) Users user)
			throws JsonProcessingException {
		return new OperateResult<String>(this.customerService.updateCustomer(customerId, params, user));

	}

	@RequestMapping(value = "queryConditions", method = RequestMethod.GET)
	public OperateResult<Map<String, Object>> queryConditions(@RequestAttribute(value = LOGIN_USER) Users user) {
		return new OperateResult<Map<String, Object>>(this.customerService.queryConditions());
	}

	/**
	 * 客户详细
	 * 
	 * @param customerId
	 * @param user
	 * @return
	 */
	@GetMapping(value = "{customerId}")
	public OperateResult<Map<String, Object>> queryDetail(@PathVariable("customerId") Integer customerId,
			@RequestAttribute(value = LOGIN_USER) Users user) {
		return new OperateResult<Map<String, Object>>(this.customerService.queryDetail(customerId, user));
	}

	/**
	 * 删除客户
	 * 
	 * @param customerId
	 * @param user
	 * @return
	 */
	@DeleteMapping(value = "{customerId}")
	public OperateResult<String> delete(@PathVariable("customerId") Integer customerId,
			@RequestAttribute(value = LOGIN_USER) Users user) {
		return new OperateResult<String>(this.customerService.deleteCustomer(user, customerId));
	}

	/**
	 * FIXME YSTCRM-280 1. 普通员工-客户列表只能显示归属自己的客户。 2. 部门主管可以查看本部门所有客户，以及下级部门所有客户。
	 * 
	 * @param curPage
	 * @param perPageSum
	 * @param current
	 * @param belongIndustry
	 * @param dealFrequency
	 * @param isPlc
	 * @param keyword
	 * @param name
	 * @param natrue
	 * @param turnover
	 * @param type
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
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
			@RequestParam(value = "type", required = false) String type)
			throws JsonParseException, JsonMappingException, IOException {
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
			params.put("natrue", natrue);
		if (StringUtils.isNotEmpty(turnover))
			params.put("turnover", turnover);
		if (StringUtils.isNotEmpty(type))
			params.put("type", type);
		Page page = new Page();
		page.setCurPage(curPage);
		page.setPerPageSum(perPageSum);
		return new OperateResult<Map<String, Object>>(this.customerService.queryCustomers(current, params, page));
	}

	@PostMapping(value = "batch/managerOwner")
	public OperateResult<String> updateManagerOwners(@RequestAttribute(value = LOGIN_USER) Users user,
			@RequestBody List<CustomerListDto> dtos) {
		return new OperateResult<String>(customerService.updateMangerOwnersBatch(user, dtos));
	}

}
