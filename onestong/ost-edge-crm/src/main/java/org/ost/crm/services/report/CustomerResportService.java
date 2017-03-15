package org.ost.crm.services.report;

import java.util.List;
import java.util.Map;

import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.entity.customer.vo.CustomerRepot;
import org.ost.entity.user.Users;
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

	public Map<String, Object> queryCustomers(Users current, Map<String, String> params, Page page) {
		
		// 调用  customerServiceClient   根据 时间段   和 所属客户经理 查询客户 信息
		// result =  customerSericeClient.queryByUser(userName);
		
		//return result;
		
		
		return null;
	}

}
