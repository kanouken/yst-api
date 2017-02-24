package org.ost.contacts.dao.address;

import org.ost.entity.contacts.address.ContactsAddress;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ContactsAddressDao extends Mapper<ContactsAddress> {
     // List<ContactListDto> getCustomerList(RowBounds rb);
}
