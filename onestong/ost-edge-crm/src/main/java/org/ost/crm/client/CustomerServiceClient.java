package org.ost.crm.client;

import org.ost.entity.customer.Customer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="customerService")
public interface CustomerServiceClient {
	@RequestMapping(method = RequestMethod.POST, value = "customer/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void createCustomer(@RequestBody Customer customer);

}
