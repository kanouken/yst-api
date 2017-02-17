package org.ost.customers.services;

import org.ost.customers.dao.CustomerDao;
import org.ost.entity.customer.Customer;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createCustomer(Customer customer) {

	}

	@Transactional(readOnly = true)
	public void queryCustomers(Users u) {

	}
}
