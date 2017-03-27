package org.ost.contacts.dao;

import org.ost.entity.contacts.visit.VisitContacts;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface VisitContactsDao extends Mapper<VisitContacts> {

}
