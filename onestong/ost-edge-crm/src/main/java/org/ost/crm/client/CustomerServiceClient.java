package org.ost.crm.client;

import java.util.List;

import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.user.Users;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "customerService")
public interface CustomerServiceClient {

	@RequestMapping(method = RequestMethod.PUT, value = "customer", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void createCustomer(@RequestBody Customer customer);

	@RequestMapping(value = "customer/", method = RequestMethod.POST)
	public PageEntity<CustomerListDto> queryMember(
			@RequestHeader(value = "curPage", defaultValue = "1") Integer curPage,
			@RequestHeader(value = "perPageSum", defaultValue = "20") Integer perPageSum,
			@RequestBody Customer customer);

}
