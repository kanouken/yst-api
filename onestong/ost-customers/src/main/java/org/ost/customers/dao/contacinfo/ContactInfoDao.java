package org.ost.customers.dao.contacinfo;

import org.ost.entity.customer.contacts.ContactsInfo;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface ContactInfoDao extends Mapper<ContactsInfo> {

}
