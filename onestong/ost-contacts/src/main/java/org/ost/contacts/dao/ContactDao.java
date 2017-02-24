package org.ost.contacts.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.ost.entity.contacts.Contacts;
import org.ost.entity.contacts.dto.ContactInfoDto;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ContactDao extends Mapper<Contacts> {
      List<ContactInfoDto> getCustomerList(RowBounds rb);
      
      
}
