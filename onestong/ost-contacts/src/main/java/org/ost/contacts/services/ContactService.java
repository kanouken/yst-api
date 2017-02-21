package org.ost.contacts.services;

import org.ost.contacts.dao.ContactDao;
import org.ost.entity.contacts.vo.ContactUpdateVo;
import org.ost.entity.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

	@Autowired
	private ContactDao contactDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createCustomer(ContactUpdateVo contactUpdateVo) {

	}
	
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateContact(ContactUpdateVo contactUpdateVo) {

	}

	@Transactional(readOnly = true)
	public void queryCustomers(Users u) {

	}
}
