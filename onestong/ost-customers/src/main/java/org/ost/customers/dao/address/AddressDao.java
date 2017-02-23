package org.ost.customers.dao.address;

import org.ost.entity.customer.address.Address;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;
@Repository
public interface AddressDao extends Mapper<Address> {

}
