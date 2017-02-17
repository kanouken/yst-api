package org.ost.crm.controller.customer;

import javax.servlet.http.HttpServletRequest;

import org.ost.crm.controller.base.Action;
import org.ost.crm.services.customer.CustomerService;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.customer.vo.CustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class CustomerController extends Action{
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="customer",method = RequestMethod.POST)
	public Object queryCustomers(HttpServletRequest request,@RequestBody CustomerCreateVo vo) throws JsonProcessingException{
		return this.customerService.createCustomer(currentUser(),vo);
		
	}
	
}
