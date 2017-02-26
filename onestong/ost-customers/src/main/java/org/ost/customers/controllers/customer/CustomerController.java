package org.ost.customers.controllers.customer;

import org.ost.customers.services.CustomerService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class CustomerController extends Action {
	@Autowired
	private CustomerService customerService;

	@RequestMapping("{id}")
	public Object detail(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = "schemaID", required = false) String schemaId) {
		return customerService.queryDetail(id, schemaId);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = "schemaID", required = false) String schemaId, Users users) {
		customerService.deleteCustomer(id, schemaId, users);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void createCustomer(@RequestHeader(value = "schemaID", required = true) String schemaId,
			@RequestBody CustomerCreateVo customer) throws JsonProcessingException {
		customerService.createCustomer(customer);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public void updateCustomer(@RequestBody Customer customer) {
		customerService.updateCustomer(customer);
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public PageEntity<CustomerListDto> queryMember(@RequestHeader(value = "schemaID", required = true) String schemaID,
			@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestBody Customer customer) {
		customer.setSchemaId(schemaID);
		return this.customerService.queryCustomers(customer, curPage, perPageSum);
	}

}
