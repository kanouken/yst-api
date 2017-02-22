package org.ost.customers.controllers.customer;

import java.util.Map;

import javax.ws.rs.Path;

import org.ost.customers.services.CustomerService;
import org.ost.entity.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController extends Action {
	@Autowired
	private CustomerService customerService;

	@RequestMapping("{id}")
	public Object detail(@PathVariable(value = "id") Integer id,
			@RequestHeader(value = "tenantId", required = false) String tenantId) {
		return customerService.queryDetail(id, tenantId);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public void createCustomer(@RequestBody Customer customer) {
		customerService.createCustomer(customer);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void updateCustomer(@RequestBody Customer customer) {
		customerService.updateCustomer(customer);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Object queryMember(@RequestHeader(value = PAGE_CURRENT, defaultValue = PAGE_CURRENT_DEFAULT) Integer curPage,
			@RequestHeader(value = PAGE_PER_SIZE, defaultValue = PAGE_PER_SIZE_DEFAULT) Integer perPageSum,
			@RequestBody Map<String, String> params) {
		return this.customerService.queryCustomers(params, curPage, perPageSum);
	}
}
