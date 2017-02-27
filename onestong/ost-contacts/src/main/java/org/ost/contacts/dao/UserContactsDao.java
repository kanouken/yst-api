package org.ost.contacts.dao;

import org.ost.entity.contacts.UserContacts;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserContactsDao extends Mapper<UserContacts> {

	

}
