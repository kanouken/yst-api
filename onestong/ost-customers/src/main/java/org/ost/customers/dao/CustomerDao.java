package org.ost.customers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ost.entity.customer.Customer;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CustomerDao extends Mapper<Customer> {

	List<Customer> selectCustomers(@Param("paramsMap") Map<String, String> params, @Param("tenantId") String tenantId,
			RowBounds rb);

	int updateCustomerSelective(@Param("customer") Customer customer);
}
