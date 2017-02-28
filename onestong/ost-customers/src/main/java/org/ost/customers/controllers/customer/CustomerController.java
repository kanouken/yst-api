package org.ost.customers.controllers.customer;

import java.util.List;

import org.common.tools.OperateResult;
import org.ost.customers.services.CustomerService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerUpdateDto;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import sun.tools.tree.ThisExpression;

@RestController
public class CustomerController extends Action {
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/{id}")
	public OperateResult<CustomerDetailDto> detail(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = "schemaID", required = false) String schemaId) {
		return new OperateResult<CustomerDetailDto>(customerService.queryDetail(id, schemaId));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public OperateResult<Integer> delete(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = "schemaID", required = false) String schemaId, Users users) {
		return new OperateResult<Integer>(customerService.deleteCustomer(id, schemaId, users));
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public OperateResult<CustomerCreateVo> createCustomer(
			@RequestHeader(value = "schemaID", required = true) String schemaId, @RequestBody CustomerCreateVo customer)
			throws JsonProcessingException {
		return new OperateResult<CustomerCreateVo>(customerService.createCustomer(customer));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public OperateResult<Integer> updateCustomer(@PathVariable(value = "id") Integer customerId,
			@RequestBody CustomerUpdateDto updateDto) {
		return new OperateResult<Integer>(customerService.updateCustomer(updateDto));
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public OperateResult<PageEntity<CustomerListDto>> queryMember(
			@RequestHeader(value = "schemaID", required = true) String schemaID,
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
	public OperateResult<String> createCustomerProject(@RequestHeader(value = "schemaID") String schemaID,
			@RequestParam(value="customerId") Integer customerId,@RequestParam(value="projectId") Integer projectId,
			Users users
			) {
		return new OperateResult<String>(this.customerService.createCustomerProject(users,customerId,projectId));
	}
	
	

}
