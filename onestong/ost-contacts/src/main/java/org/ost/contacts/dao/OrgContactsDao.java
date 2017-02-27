package org.ost.contacts.dao;

import org.ost.entity.contacts.OrgContacts;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrgContactsDao extends Mapper<OrgContacts> {

}
