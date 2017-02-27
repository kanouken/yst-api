package org.ost.contacts.dao;

import org.ost.entity.customer.CustomerContacts;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CustomerContactsDao extends Mapper<CustomerContacts> {

}
