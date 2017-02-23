package org.ost.customers.dao.contacinfo;

import org.ost.entity.customer.contacts.ContactInfo;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface ContactInfoDao extends Mapper<ContactInfo> {

}
