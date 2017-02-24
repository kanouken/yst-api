package org.ost.contacts.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.common.tools.pinyin.Chinese2PY;
import org.ost.contacts.dao.ContactDao;
import org.ost.contacts.dao.address.ContactsAddressDao;
import org.ost.contacts.dao.files.ContactsFileDao;
import org.ost.contacts.dao.info.ContactsInfoDao;
import org.ost.contacts.model.ContactsAddressExample;
import org.ost.contacts.model.ContactsFileExample;
import org.ost.contacts.model.ContactsInfoExample;
import org.ost.entity.contacts.Contacts;
import org.ost.entity.contacts.address.ContactsAddress;
import org.ost.entity.contacts.contactsinfo.ContactsInfo;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.file.ContactsFile;
import org.ost.entity.contacts.mapper.ContactsEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactsService {
	private Logger logger = LoggerFactory.getLogger(ContactsService.class);
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private ContactsAddressDao addressDao;

	@Autowired
	private ContactsInfoDao infoDao;
	@Autowired
	private ContactsFileDao fileDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createContacts(ContactsDto contactsDto) {
		Contacts contact = new Contacts();
		contact.setCreateTime(new Date());
		contact.setUpdateTime(new Date());
		contact.setCreateBy(contactsDto.getCurrentUserName());
		contact.setUpdateBy(contactsDto.getCurrentUserName());
		contact.setDept(contactsDto.getDept());
		contact.setFirstName(contactsDto.getName().substring(0, 1));
		contact.setLastName(contactsDto.getName().substring(1, contactsDto.getName().length()));
		contact.setPosition(contactsDto.getPosition());
		String pinyin = null;
		if (StringUtils.isNotEmpty((pinyin = Chinese2PY.getPinYin(contactsDto.getName())))) {
			contact.setPy(pinyin);
		} else {
			contact.setPy(contactsDto.getName());
		}
		contact.setHeadPic(contactsDto.getHeadPic());
		contact.setSex(contactsDto.getSex());
		contact.setTenantId(contactsDto.getTenantId());
		contactDao.insert(contact);

		contactsDto.getLocations().forEach(address -> {
			ContactsAddress _address = ContactsEntityMapper.INSTANCE.contactsAddressDtoToContactsAddress(address,
					contactsDto);
			_address.setContactId(contact.getId());
			this.addressDao.insert(_address);
		});

		contactsDto.getPhone().forEach(contactsInfo -> {
			ContactsInfo _contactsInfo = ContactsEntityMapper.INSTANCE.contactsInfoDtoToContactsInfo(contactsInfo,
					contactsDto);
			_contactsInfo.setContactId(contact.getId());
			this.infoDao.insert(_contactsInfo);
		});
		// files
		contactsDto.getPhoto().forEach(contactsFile -> {
			ContactsFile _contactsFile = ContactsEntityMapper.INSTANCE.contactsFileDtoToContactsFile(contactsFile,
					contactsDto);
			_contactsFile.setContactID(contact.getId());
			this.fileDao.insert(_contactsFile);
		});
		logger.info("an new contacts created");

	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateContacts(Integer id, ContactsDto contactsDto) {
		Contacts contact = new Contacts();
		contact.setId(id);
		contact.setCreateTime(new Date());
		contact.setUpdateTime(new Date());
		contact.setCreateBy(contactsDto.getCurrentUserName());
		contact.setUpdateBy(contactsDto.getCurrentUserName());
		contact.setDept(contactsDto.getDept());
		contact.setFirstName(contactsDto.getName().substring(0, 1));
		contact.setLastName(contactsDto.getName().substring(1, contactsDto.getName().length()));
		contact.setPosition(contactsDto.getPosition());
		String pinyin = null;
		if (StringUtils.isNotEmpty((pinyin = Chinese2PY.getPinYin(contactsDto.getName())))) {
			contact.setPy(pinyin);
		} else {
			contact.setPy(contactsDto.getName());
		}
		contact.setHeadPic(contactsDto.getHeadPic());
		contact.setSex(contactsDto.getSex());
		contact.setTenantId(contactsDto.getTenantId());
		contactDao.updateByPrimaryKeySelective(contact);
		// clean address
		ContactsAddressExample addressExample = new ContactsAddressExample();
		addressExample.createCriteria().andTenantIdEqualTo(contactsDto.getTenantId()).andContactidEqualTo(id);
		ContactsAddress ca = new ContactsAddress();
		ca.setUpdateTime(new Date());
		ca.setIsDelete(Short.valueOf("1"));

		this.addressDao.updateByExampleSelective(ca, addressExample);

		contactsDto.getLocations().forEach(address -> {
			ContactsAddress _address = ContactsEntityMapper.INSTANCE.contactsAddressDtoToContactsAddress(address,
					contactsDto);
			_address.setContactId(contact.getId());
			this.addressDao.insert(_address);
		});
		// clean phone
		ContactsInfoExample contactsInfoExample = new ContactsInfoExample();
		contactsInfoExample.createCriteria().andTenantIdEqualTo(contactsDto.getTenantId()).andContactidEqualTo(id);
		ContactsInfo cinfo = new ContactsInfo();
		cinfo.setIsDelete(Short.valueOf("1"));
		cinfo.setUpdateTime(new Date());
		this.infoDao.updateByExampleSelective(cinfo, contactsInfoExample);
		contactsDto.getPhone().forEach(contactsInfo -> {
			ContactsInfo _contactsInfo = ContactsEntityMapper.INSTANCE.contactsInfoDtoToContactsInfo(contactsInfo,
					contactsDto);
			_contactsInfo.setContactId(contact.getId());
			this.infoDao.insert(_contactsInfo);
		});
		// clean file
		ContactsFileExample contactsFileExample = new ContactsFileExample();
		contactsFileExample.createCriteria().andTenantIdEqualTo(contactsDto.getTenantId()).andContactidEqualTo(id);
		ContactsFile cFile = new ContactsFile();
		cFile.setIsDelete(Short.valueOf("1"));
		cFile.setUpdateTime(new Date());
		this.fileDao.updateByExampleSelective(cFile, contactsFileExample);
		// files
		contactsDto.getPhoto().forEach(contactsFile -> {
			ContactsFile _contactsFile = ContactsEntityMapper.INSTANCE.contactsFileDtoToContactsFile(contactsFile,
					contactsDto);
			_contactsFile.setContactID(contact.getId());
			this.fileDao.insert(_contactsFile);
		});
		logger.info("an  contacts updated");
	}

	@Transactional(readOnly = true)
	public Object queryContacts(Integer contactId, Integer curPage, Integer perPageSum) {
		// Contacts contact = new Contacts();
		// contact.setId(contactId);
		// Page page = new Page();
		// page.setCurPage(curPage.intValue());
		// page.setPerPageSum(perPageSum.intValue());
		// RowBounds rb = new RowBounds(page.getNextPage(),
		// page.getPerPageSum());
		// Integer total = contactDao.selectCount(contact);
		// Map resultMap = new HashMap();
		// page.setTotalRecords(total);
		// resultMap.put("page", page);
		// resultMap.put("objects", contactDao.selectByRowBounds(contact, rb));
		// return resultMap;
		return null;
	}

	@Transactional(readOnly = true)
	public Object getContactsDetail(Integer id, String tenantId) {
		Contacts contacts = this.contactDao.selectByPrimaryKey(id);
		ContactsAddress ca = new ContactsAddress();
		ca.setIsDelete(Short.valueOf("0"));
		ca.setContactId(contacts.getId());
		List<ContactsAddress> cas = this.addressDao.select(ca);
		ContactsInfo info = new ContactsInfo();
		info.setContactId(contacts.getId());
		info.setIsDelete(Short.valueOf("0"));
		List<ContactsInfo> infos = this.infoDao.select(info);

		ContactsFile cFile = new ContactsFile();
		cFile.setContactID(contacts.getId());
		cFile.setIsDelete(Short.valueOf("0"));
		List<ContactsFile> cFiles = this.fileDao.select(cFile);

		ContactsDto dto = ContactsEntityMapper.INSTANCE.contactsToContactsDto(contacts);
		dto.setLocations(ContactsEntityMapper.INSTANCE.contactsAddressToContactsAddressDto(cas));
		dto.setPhone(ContactsEntityMapper.INSTANCE.contactsInfoToContactsInfoDto(infos));
		dto.setPhoto(ContactsEntityMapper.INSTANCE.contactsFileToContactsFileDto(cFiles));
		return dto;
	}

}
