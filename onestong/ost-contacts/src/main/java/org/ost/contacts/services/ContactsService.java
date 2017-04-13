package org.ost.contacts.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.pinyin.Chinese2PY;
import org.ost.contacts.dao.ContactDao;
import org.ost.contacts.dao.CustomerContactsDao;
import org.ost.contacts.dao.ProjectContactsDao;
import org.ost.contacts.dao.VisitContactsDao;
import org.ost.contacts.dao.address.ContactsAddressDao;
import org.ost.contacts.dao.files.ContactsFileDao;
import org.ost.contacts.dao.info.ContactsInfoDao;
import org.ost.contacts.model.ContactsAddressExample;
import org.ost.contacts.model.ContactsExample;
import org.ost.contacts.model.ContactsFileExample;
import org.ost.contacts.model.ContactsInfoExample;
import org.ost.contacts.model.CustomerContactsExample;
import org.ost.contacts.model.ProjectContactsExample;
import org.ost.entity.base.PageEntity;
import org.ost.entity.contacts.Contacts;
import org.ost.entity.contacts.CustomerContacts;
import org.ost.entity.contacts.ProjectContacts;
import org.ost.entity.contacts.address.ContactsAddress;
import org.ost.entity.contacts.contactsinfo.ContactsInfo;
import org.ost.entity.contacts.contactsinfo.dto.ContactsInfoDto;
import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.contacts.dto.VisitContactsDto;
import org.ost.entity.contacts.file.ContactsFile;
import org.ost.entity.contacts.mapper.ContactsEntityMapper;
import org.ost.entity.contacts.visit.VisitContacts;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.project.dto.ProjectContactsDto;
import org.ost.entity.user.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@Autowired
	private ProjectContactsDao pcDao;

	@Autowired
	CustomerContactsDao customerContactsDao;

	@Autowired
	VisitContactsDao visitContactsDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public ContactsDto createContacts(ContactsDto contactsDto) {
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
		contact.setSzm(Chinese2PY.getSzm(contactsDto.getName()));
		contact.setHeadPic(contactsDto.getHeadPic());
		contact.setSex(contactsDto.getSex());
		contact.setSchemaId(contactsDto.getSchemaId());
		contactDao.insertSelective(contact);

		if (contactsDto.getCustomer() != null) {
			CustomerContacts cc = new CustomerContacts();
			cc.setContactID(contact.getId());
			cc.setCreateBy(contact.getCreateBy());
			cc.setUpdateBy(contact.getUpdateBy());
			cc.setCustomerID(contactsDto.getCustomer().getId());
			cc.setSchemaId(contact.getSchemaId());
			customerContactsDao.insertSelective(cc);

		}
		if (CollectionUtils.isNotEmpty(contactsDto.getLocations())) {
			contactsDto.getLocations().forEach(address -> {
				ContactsAddress _address = ContactsEntityMapper.INSTANCE.contactsAddressDtoToContactsAddress(address,
						contactsDto);
				_address.setContactId(contact.getId());
				this.addressDao.insert(_address);
			});

		}

		if (CollectionUtils.isNotEmpty(contactsDto.getEmail())) {
			contactsDto.getEmail().forEach(email -> {
				contactsDto.getPhone().add(new ContactsInfoDto("email", null, email));
			});
		}

		contactsDto.getPhone().forEach(contactsInfo -> {
			ContactsInfo _contactsInfo = ContactsEntityMapper.INSTANCE.contactsInfoDtoToContactsInfo(contactsInfo,
					contactsDto);
			_contactsInfo.setContactId(contact.getId());
			this.infoDao.insert(_contactsInfo);
		});

		// files
		if (CollectionUtils.isNotEmpty(contactsDto.getPhoto())) {
			contactsDto.getPhoto().forEach(contactsFile -> {
				ContactsFile _contactsFile = ContactsEntityMapper.INSTANCE.contactsFileDtoToContactsFile(contactsFile,
						contactsDto);
				_contactsFile.setContactID(contact.getId());
				this.fileDao.insert(_contactsFile);
			});
		}

		logger.info("an new contacts created");
		return contactsDto;

	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public ContactsDto updateContacts(Integer id, String schemaId, ContactsDto contactsDto) {

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
		if (StringUtils.isNotEmpty(contactsDto.getSex())) {
			contact.setSex(contactsDto.getSex());
		}
		contact.setSchemaId(contactsDto.getSchemaId());
		contactDao.updateByPrimaryKeySelective(contact);

		// 更新 客户联系人 关系
		if (contactsDto.getCustomer() != null) {
			// old
			CustomerContactsExample cce = new CustomerContactsExample();
			cce.createCriteria().andSchemaidEqualTo(schemaId).andIsdeleteEqualTo(Byte.parseByte("0"))
					.andContactidEqualTo(id);
			CustomerContacts cc = new CustomerContacts();
			cc.setUpdateBy(contactsDto.getCurrentUserName());
			cc.setUpdateTime(contact.getUpdateTime());
			cc.setCustomerID(contactsDto.getCustomer().getId());
			this.customerContactsDao.updateByExampleSelective(cc, cce);
		}
		// clean address
		ContactsAddressExample addressExample = new ContactsAddressExample();
		addressExample.createCriteria().andSchemaidEqualTo(contactsDto.getSchemaId()).andContactidEqualTo(id);
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
		contactsInfoExample.createCriteria().andSchemaidEqualTo(contactsDto.getSchemaId()).andContactidEqualTo(id);
		ContactsInfo cinfo = new ContactsInfo();
		cinfo.setIsDelete(Short.valueOf("1"));
		cinfo.setUpdateTime(new Date());
		this.infoDao.updateByExampleSelective(cinfo, contactsInfoExample);

		if (CollectionUtils.isNotEmpty(contactsDto.getEmail())) {
			contactsDto.getEmail().forEach(email -> {
				contactsDto.getPhone().add(new ContactsInfoDto("email", null, email));
			});
		}
		contactsDto.getPhone().forEach(contactsInfo -> {
			ContactsInfo _contactsInfo = ContactsEntityMapper.INSTANCE.contactsInfoDtoToContactsInfo(contactsInfo,
					contactsDto);
			_contactsInfo.setContactId(contact.getId());
			this.infoDao.insert(_contactsInfo);
		});
		// clean file
		ContactsFileExample contactsFileExample = new ContactsFileExample();
		contactsFileExample.createCriteria().andSchemaidEqualTo(contactsDto.getSchemaId()).andContactidEqualTo(id);
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
		return contactsDto;
	}

	@Transactional(readOnly = true)
	public PageEntity<ContactsListDto> queryContacts(String tenantId, Integer curPage, Integer perPageSum, String email,
			String name, String phone, String keyword, Integer customerID) {
		logger.info("调用参数 keyword" + keyword + " name-" + name);

		PageEntity<ContactsListDto> pages = new PageEntity<ContactsListDto>();
		List<ContactsListDto> contacts = new ArrayList<ContactsListDto>();
		Integer totalRecords = this.contactDao.selectCountContacts(name, phone, keyword, email, customerID);
		RowBounds rb = new RowBounds();
		if (totalRecords > 0) {
			contacts = this.contactDao.selectContacts(name, phone, keyword, email, customerID, rb);
		}
		pages.setCurPage(curPage);
		pages.setTotalRecord(totalRecords);
		pages.setObjects(contacts);
		return pages;

	}

	@Transactional(readOnly = true)
	public ContactsDto getContactsDetail(Integer id, String tenantId) {
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
		List<ContactsInfo> _tmp = null;
		if (CollectionUtils.isNotEmpty(infos)) {
			_tmp = infos.stream().filter(i -> !i.getType().equals("email")).collect(Collectors.toList());
		}
		dto.setPhone(ContactsEntityMapper.INSTANCE.contactsInfoToContactsInfoDto(_tmp));
		dto.setPhoto(ContactsEntityMapper.INSTANCE.contactsFileToContactsFileDto(cFiles));

		List<String> emails = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(dto.getPhone())) {
			emails = infos.stream().filter(ci -> ci.getType().equals("email")).map(ci -> ci.getVal())
					.collect(Collectors.toList());
		}
		dto.setEmail(emails);
		CustomerContacts cc = new CustomerContacts();

		cc.setContactID(contacts.getId());
		cc.setIsDelete(contacts.getIsDelete());
		List<CustomerContacts> ccs = this.customerContactsDao.select(cc);
		if (CollectionUtils.isNotEmpty(ccs)) {
			// 查询用户名?
			dto.setCustomer(new CustomerVo(ccs.get(0).getCustomerID(), ""));
		}
		return dto;
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String deleteContacts(Integer id, String schemaId,String accountName) {
		Contacts contacts = new Contacts();
		contacts.setUpdateBy(accountName);
		contacts.setUpdateTime(new Date());
		contacts.setIsDelete(Short.parseShort("1"));
		ContactsExample ce = new ContactsExample();
		ce.createCriteria().andIdEqualTo(id).andSchemaidEqualTo(schemaId);
		this.contactDao.updateByExampleSelective(contacts, ce);
		// update address and info
		ContactsAddress ca = new ContactsAddress();
		ca.setIsDelete(Short.parseShort("1"));
		ca.setUpdateBy(accountName);
		ca.setUpdateTime(new Date());
		ContactsAddressExample cae = new ContactsAddressExample();
		cae.createCriteria().andContactidEqualTo(id).andSchemaidEqualTo(schemaId);
		this.addressDao.updateByExample(ca, cae);
		ContactsInfo ci = new ContactsInfo();
		ci.setIsDelete(Short.parseShort("1"));
		ci.setUpdateBy(accountName);
		ci.setUpdateTime(new Date());
		ContactsInfoExample cie = new ContactsInfoExample();
		cie.createCriteria().andContactidEqualTo(id).andSchemaidEqualTo(schemaId);
		this.infoDao.updateByExample(ci, cie);
		ContactsFile cf = new ContactsFile();
		cf.setIsDelete(Short.parseShort("1"));
		cf.setUpdateBy(accountName);
		cf.setUpdateTime(new Date());
		ContactsFileExample cfe = new ContactsFileExample();
		cfe.createCriteria().andSchemaidEqualTo(schemaId).andContactidEqualTo(id);
		this.fileDao.updateByExample(cf, cfe);

		return HttpStatus.OK.name();
	}

	/**
	 * 新增 ／ 更新项目联系人关系
	 * 
	 * @param schemaID
	 * @param dto
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String createProjectContacts(String schemaID, ProjectContactsDto dto) {
		dto.getContacts().forEach(contacts -> {
			ProjectContacts pContacts = new ProjectContacts();
			pContacts.setContactID(contacts.getId());
			pContacts.setProjectID(dto.getProject().getId());
			pContacts.setCreateBy(dto.getUserName());
			pContacts.setSchemaId(schemaID);
			pContacts.setUpdateBy(dto.getUserName());
			pContacts.setCreateTime(new Date());
			pContacts.setUpdateTime(new Date());
			pContacts.setRole(contacts.getRole());
			this.pcDao.insertSelective(pContacts);
		});
		return HttpStatus.OK.name();
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateConactsProject(String schemaID, ProjectContactsDto dto) {
		ProjectContactsExample pce = new ProjectContactsExample();
		pce.createCriteria().andSchemaidEqualTo(schemaID).andProjectidEqualTo(dto.getProject().getId())
				.andIsdeleteEqualTo(Byte.parseByte("0"));
		ProjectContacts pContacts = new ProjectContacts();
		pContacts.setIsDelete(Short.valueOf("1"));
		pContacts.setUpdateTime(new Date());
		pContacts.setUpdateBy(dto.getUserName());
		pContacts.setProjectID(dto.getProject().getId());
		this.pcDao.updateByExampleSelective(pContacts, pce);

		dto.getContacts().forEach(contacts -> {
			ProjectContacts _pContacts = new ProjectContacts();
			_pContacts.setContactID(contacts.getId());
			_pContacts.setProjectID(dto.getProject().getId());
			_pContacts.setCreateBy(contacts.getCurrentUserName());
			_pContacts.setSchemaId(schemaID);
			_pContacts.setUpdateBy(contacts.getCurrentUserName());
			_pContacts.setRole(contacts.getRole());
			this.pcDao.insertSelective(_pContacts);
		});
		return HttpStatus.OK.name();
	}

	@Transactional(readOnly = true)
	public List<ContactsListDto> queryByProject(String schemaID, Integer projectId) {
		List<ContactsListDto> contactsListDtos = this.contactDao.selectByProject(schemaID, projectId);
		return contactsListDtos;
	}

	
}
