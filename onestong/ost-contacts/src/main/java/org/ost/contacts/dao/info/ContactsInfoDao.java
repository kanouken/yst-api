package org.ost.contacts.dao.info;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.ost.entity.contacts.Contacts;
import org.ost.entity.contacts.contactsinfo.ContactsInfo;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ContactsInfoDao extends Mapper<ContactsInfo> {
      
}
