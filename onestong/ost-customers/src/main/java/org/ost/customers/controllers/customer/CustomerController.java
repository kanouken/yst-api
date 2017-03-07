package org.ost.customers.controllers.customer;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.customers.services.CustomerService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.CustomerProject;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerProjectDto;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "customer")
public class CustomerController extends Action {
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public OperateResult<CustomerDetailDto> detail(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = TENANT_ID, required = false) String schemaId) {
		return new OperateResult<CustomerDetailDto>(customerService.queryDetail(id, schemaId));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public OperateResult<Integer> delete(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = TENANT_ID, required = false) String schemaId, Users users) {
		return new OperateResult<Integer>(customerService.deleteCustomer(id, schemaId, users));
	}

	/**
	 * 新增客户
	 * 
	 * @param schemaId
	 * @param customer
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public OperateResult<CustomerCreateVo> createCustomer(
			@RequestHeader(value = TENANT_ID, required = true) String schemaId, @RequestBody CustomerCreateVo customer)
			throws JsonProcessingException {
		return new OperateResult<CustomerCreateVo>(customerService.createCustomer(schemaId, customer));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public OperateResult<String> updateCustomer(@PathVariable(value = "id") Integer customerId,
			@RequestHeader(value = TENANT_ID) String schemaId, @RequestBody CustomerCreateVo updateDto)
			throws JsonProcessingException {
		return new OperateResult<String>(customerService.updateCustomer(customerId, schemaId, updateDto));
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public OperateResult<PageEntity<CustomerListDto>> queryMember(
			@RequestHeader(value = TENANT_ID, required = true) String schemaID,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestBody Customer customer) {
		customer.setSchemaId(schemaID);
		return new OperateResult<PageEntity<CustomerListDto>>(
				this.customerService.queryCustomers(customer, curPage, perPageSum));
	}

	@Deprecated
	@RequestMapping(value = "/queryByContacts", method = RequestMethod.GET)
	public OperateResult<CustomerListDto> queryDetailByContacts(@RequestHeader(value = "schemaID") String schemaID,
			@RequestParam(value = "contactsId") Integer contactsId) {
		return new OperateResult<CustomerListDto>(customerService.queryByContacts(schemaID, contactsId));
	}

	@RequestMapping(value = "/queryByIds", method = RequestMethod.GET)
	public OperateResult<List<CustomerListDto>> queryByIds(@RequestHeader(value = "schemaID") String schemaID,
			@RequestParam(value = "ids") Integer[] ids) {
		return new OperateResult<List<CustomerListDto>>(customerService.queryByIds(schemaID, ids));
	}

	@RequestMapping(value = "/project", method = RequestMethod.POST)
	public OperateResult<String> createCustomerProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody CustomerProjectDto dto) {
		return new OperateResult<String>(this.customerService.createCustomerProject(schemaID, dto));
	}
	
	@RequestMapping(value = "/project", method = RequestMethod.PUT)
	public OperateResult<String> updateCustomerProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestBody CustomerProjectDto dto) {
		return new OperateResult<String>(this.customerService.updateCustomerProject(schemaID, dto));
	}
	
	@GetMapping(value="/queryByProject")
	public OperateResult<CustomerVo> queryByProject(@RequestHeader(value = TENANT_ID) String schemaID,
			@RequestParam(value="projectId") Integer projectId){
		return new OperateResult<CustomerVo>(this.customerService.queryByProject(schemaID,projectId));
	}
}
