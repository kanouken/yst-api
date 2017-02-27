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

	List<Customer> selectCustomers(@Param("paramsMap") Map<String, String> params, @Param("customer") Customer customer,
			RowBounds rb);

	int updateCustomerSelective(@Param("customer") Customer customer);

	Integer selectCustomerCount(@Param("params") Map<String, String> params, @Param("customer") Customer customer);

	List<Customer> selectByIds(@Param("ids") Integer[] ids);
}
