package org.ost.customers.dao;

import org.ost.entity.customer.Customer;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CustomerDao extends Mapper<Customer> {

}
