package org.ost.crm.client;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.user.Users;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customerService")
public interface CustomerServiceClient extends BaseClient {

	@RequestMapping(value = "customer/", method = RequestMethod.POST)
	public OperateResult<CustomerCreateVo> createCustomer(
			@RequestHeader(value = TENANT_ID, required = true) String schemaId, @RequestBody CustomerCreateVo customer);

	@RequestMapping(value = "customer/list/", method = RequestMethod.POST)
	public OperateResult<PageEntity<CustomerListDto>> queryMember(@RequestHeader(value = TENANT_ID, required = true) String schemaId,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestBody Customer customer);

	@RequestMapping("customer/{id}/")
	public OperateResult<CustomerDetailDto> queryDetail(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = TENANT_ID, required = false) String schemaId);

	@RequestMapping(value = "customer/{id}", method = RequestMethod.DELETE)
	public OperateResult<Integer> deleteCustomer(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = TENANT_ID, required = false) String schemaId, Users users);

	@RequestMapping(value = "customer/{id}", method = RequestMethod.PUT)
	public OperateResult<String> updateCustomer(@PathVariable(value = "id") Integer customerId,
			@RequestHeader(value = TENANT_ID) String schemaId, @RequestBody CustomerCreateVo updateDto);

	@RequestMapping(value = "/queryByContacts", method = RequestMethod.GET)
	public OperateResult<CustomerListDto> queryDetailByContacts(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestParam(value = "contactsId") Integer contactsId);

	@RequestMapping(value = "customer/queryByIds", method = RequestMethod.GET)
	public OperateResult<List<CustomerListDto>> queryByIds(@RequestHeader(value = "schemaID") String schemaID,
			@RequestParam(value = "ids") int[] ids);

	@RequestMapping(value = "customer/project/", method = RequestMethod.POST)
	public OperateResult<String> createCustomerProject(@RequestHeader(value = "schemaID") String schemaID,
			@RequestParam(value = "customerId") Integer customerId,
			@RequestParam(value = "projectId") Integer projectId, Users users);

}
