package org.ost.crm.client;

import org.common.tools.OperateResult;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerUpdateDto;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customerService")
public interface CustomerServiceClient {

	@RequestMapping(method = RequestMethod.POST, value = "customer/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void createCustomer(@RequestHeader(value = "schemaID", required = true) String schemaId,
			@RequestBody CustomerCreateVo customer);

	@RequestMapping(value = "customer/list/", method = RequestMethod.POST)
	public PageEntity<CustomerListDto> queryMember(@RequestHeader(value = "schemaID", required = true) String schemaId,
			@RequestHeader(value = "curPage", defaultValue = "1") Integer curPage,
			@RequestHeader(value = "perPageSum", defaultValue = "20") Integer perPageSum,
			@RequestBody Customer customer);

	@RequestMapping("customer/{id}/")
	public CustomerDetailDto queryDetail(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = "schemaID", required = false) String schemaId);

	@RequestMapping(value = "customer/{id}", method = RequestMethod.PUT)
	public OperateResult<Integer> updateCustomer(@PathVariable(value = "id") Integer customerId,
			@RequestBody CustomerUpdateDto updateDto);

	@RequestMapping(value = "/queryByContacts", method = RequestMethod.GET)
	public OperateResult<CustomerListDto> queryDetailByContacts(@RequestHeader(value = "schemaID") String schemaID,
			@RequestParam(value = "contactsId") Integer contactsId);
}
