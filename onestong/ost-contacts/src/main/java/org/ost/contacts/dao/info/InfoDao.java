package org.ost.contacts.dao.info;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.ost.entity.contacts.Contact;
import org.ost.entity.contacts.ContactInfo;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface InfoDao extends Mapper<ContactInfo> {
      
}
