package org.ost.customers.dao.customer;

import org.ost.customers.model.customer.Customer;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface CustomerDao extends Mapper<Customer> {

}
