package org.ost.contacts.services;

import org.ost.contacts.dao.ContactDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
	final protected Logger LOG = LoggerFactory.getLogger(this.getClass());

	public static final Short YES = 1;
	public static final Short NO = 0;
	
	@Autowired
	protected ContactDao  contactDao;
}
