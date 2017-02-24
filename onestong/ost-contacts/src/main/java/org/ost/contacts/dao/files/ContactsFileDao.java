package org.ost.contacts.dao.files;

import org.ost.entity.contacts.file.ContactsFile;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ContactsFileDao extends Mapper<ContactsFile> {

}
