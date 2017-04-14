package org.ost.contacts.services.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.ost.contacts.dao.CustomerContactsDao;
import org.ost.contacts.services.BaseService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerContactsService extends BaseService {
	
	@Autowired
	private CustomerContactsDao  customerContactsDao;
	@Transactional(readOnly = true)
	public PageEntity<ContactsListDto> queryContactsByCustomer(String schemaID, Integer curPage, Integer perPageSum,
			String email, String name, String phone, String keyword, String customerIds) {
		LOG.info("调用参数 keyword" + keyword + " name-" + name);
		int[] _customerIds =null;
		if(StringUtils.isNotEmpty(customerIds)){
			_customerIds = Arrays.asList( customerIds.split(",")).stream().mapToInt(id->Integer.parseInt(id)).toArray();
		}
		PageEntity<ContactsListDto> pages = new PageEntity<ContactsListDto>();
		List<ContactsListDto> contacts = new ArrayList<ContactsListDto>();
		Integer totalRecords = this.customerContactsDao.selectCountContacts(name, phone, keyword, email, _customerIds);
		RowBounds rb = new RowBounds();
		if (totalRecords > 0) {
			contacts = this.customerContactsDao.selectContacts(name, phone, keyword, email, _customerIds, rb);
		}
		pages.setCurPage(curPage);
		pages.setTotalRecord(totalRecords);
		pages.setObjects(contacts);
		return pages;
	}

}
