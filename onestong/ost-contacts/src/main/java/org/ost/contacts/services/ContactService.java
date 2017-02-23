package org.ost.contacts.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.ost.contacts.dao.ContactDao;
import org.ost.contacts.dao.address.AddressDao;
import org.ost.contacts.dao.info.InfoDao;
import org.ost.entity.contacts.Address;
import org.ost.entity.contacts.Contact;
import org.ost.entity.contacts.ContactInfo;
import org.ost.entity.contacts.dto.ContactInfoDto;
import org.ost.entity.contacts.vo.ContactUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

	@Autowired
	private ContactDao contactDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private InfoDao infoDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createCustomer(ContactUpdateVo contactUpdateVo) {
		Contact contact=new Contact();
		contact.setCreateTime(new Date());
		contact.setCreateBy("1");
		contact.setDept(contactUpdateVo.getDept());
		contact.setFirstName("");
		contact.setLastName("");
		contact.setPosition(contactUpdateVo.getPosition());
		contact.setPy("");
		contact.setSex(contactUpdateVo.getSex());
		contact.setSchemaId("1");
		contactDao.insert(contact);		
		
		for(int i=0;i<contactUpdateVo.getLocationVo().size();i++){
			Address address=new Address();
			address=contactUpdateVo.getLocationVo().get(i);		
			addressDao.insert(address);
		}		
		
		for(int i=0;i<contactUpdateVo.getPhoneVo().size();i++){
			ContactInfo info=new ContactInfo();
			info=contactUpdateVo.getPhoneVo().get(i);
			infoDao.insert(info);
		}	
		
	}
	
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateContact(ContactUpdateVo contactUpdateVo) {
		Contact contact=new Contact();
		contact.setCreateTime(new Date());
		contact.setCreateBy("1");
		contact.setDept(contactUpdateVo.getDept());
		contact.setFirstName("");
		contact.setLastName("");
		contact.setPosition(contactUpdateVo.getPosition());
		contact.setPy("");
		contact.setSex(contactUpdateVo.getSex());
		contactDao.updateByExample(contact,contactUpdateVo.getId());
		
		
		for(int i=0;i<contactUpdateVo.getLocationVo().size();i++){
			Address address=contactUpdateVo.getLocationVo().get(i);
			addressDao.updateByExample(address,address.getId());
		}
		
		
		for(int i=0;i<contactUpdateVo.getPhoneVo().size();i++){
			ContactInfo info=contactUpdateVo.getPhoneVo().get(i);
		
			infoDao.updateByExample(info,info.getId());
		}
	}

	@Transactional(readOnly = true)
	public Object getCustomerList(Integer curPage,Integer perPageSum) {
		Page page = new Page();
		page.setCurPage(curPage.intValue());
		page.setPerPageSum(perPageSum.intValue());
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum()); 
		Integer total = null;
		Map resultMap=new HashMap();
		page.setTotalRecords(total);
		resultMap.put("page", page);
		resultMap.put("objects", contactDao.selectByRowBounds(null,rb));
		return resultMap;
	}
	
	@Transactional(readOnly = true)
	public Object getCustomer(Integer id) {
		ContactInfoDto contactInfoDto=new ContactInfoDto();
		List<ContactInfo> contactInfo=infoDao.selectByExample(id);
		List<Address> address=addressDao.selectByExample(id);
		Contact contact=contactDao.selectByPrimaryKey(id);
		contactInfoDto.setContactInfo(contactInfo);
		contactInfoDto.setAddress(address);
		contactInfoDto.setDept(contact.getDept());
		return contactInfoDto;     
	}
}
