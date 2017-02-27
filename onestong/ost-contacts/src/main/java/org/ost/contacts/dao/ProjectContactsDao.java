package org.ost.contacts.dao;

import org.ost.entity.contacts.ProjectContacts;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ProjectContactsDao extends Mapper<ProjectContacts> {

}
