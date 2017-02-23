package org.ost.contacts.dao.address;

import org.ost.entity.contacts.Address;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AddressDao extends Mapper<Address> {
     // List<ContactListDto> getCustomerList(RowBounds rb);
}
