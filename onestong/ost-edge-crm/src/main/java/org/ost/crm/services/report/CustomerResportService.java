package org.ost.crm.services.report;

import java.util.List;
import org.common.tools.OperateResult;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.user.UserCustomers;
import org.ost.entity.customer.vo.CustomerRepot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class CustomerResportService {

	@Autowired
	private CustomerServiceClient customerServiceClient;

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<CustomerRepot> reportCustomer(Integer userID, CustomerRepot cus) throws JsonProcessingException {
		OperateResult<List<CustomerRepot>> result = this.customerServiceClient.queryCustomerByParam(userID, cus.getId());
		return (List<CustomerRepot>) cus;
		
	}

}
