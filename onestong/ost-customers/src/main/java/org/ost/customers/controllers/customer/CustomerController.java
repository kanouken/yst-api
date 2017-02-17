package org.ost.customers.controllers.customer;

import org.ost.customers.services.CustomerService;
import org.ost.entity.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void createCustomer(@RequestBody Customer customer) {
		this.customerService.createCustomer(customer);
	}
}
