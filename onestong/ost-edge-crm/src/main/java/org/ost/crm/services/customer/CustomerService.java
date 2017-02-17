package org.ost.crm.services.customer;

import org.ost.crm.client.CustomerServiceClient;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class CustomerService {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CustomerServiceClient customerServiceClient;

	public Object createCustomer(Users user, CustomerCreateVo vo) throws JsonProcessingException {
		Customer customer = new Customer();
		customer.setName(vo.getName());
		this.customerServiceClient.createCustomer(customer);
		return vo;
	}
}
