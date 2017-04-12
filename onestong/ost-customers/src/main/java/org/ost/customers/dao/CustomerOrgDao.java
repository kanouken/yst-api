package org.ost.customers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.dto.CustomerQueryDto;
import org.ost.entity.customer.org.CustomerOrg;

import tk.mybatis.mapper.common.Mapper;

public interface CustomerOrgDao extends Mapper<CustomerOrg> {
	Integer selectCustomerCount(@Param("params") Map<String, String> params, @Param("customer") CustomerQueryDto customer, @Param("keyword") String keyword);

	List<Customer> selectCustomers(@Param("params") Map<String, String> params, @Param("customer") CustomerQueryDto customer, @Param("keyword") String keyword, RowBounds rb);

}
