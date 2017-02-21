package org.ost.customers.controllers.customer;

import javax.servlet.http.HttpServletRequest;

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

	@RequestMapping(value = "", method = RequestMethod.GET)
	public void createCustomer(HttpServletRequest request) {
		String authToken  =request.getHeader("Authorization");
		String remoteHost = request.getRemoteHost();
		String _r = request.getHeader("x-forwarded-for");
//		this.customerService.createCustomer(customer);
	}
}
