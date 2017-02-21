package org.ost.contacts.dao;

import org.ost.entity.customer.Customer;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ContactDao extends Mapper<Customer> {

}
