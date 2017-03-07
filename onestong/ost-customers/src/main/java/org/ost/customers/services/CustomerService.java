package org.ost.customers.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.common.tools.pinyin.Chinese2PY;
import org.ost.customers.dao.CustomerDao;
import org.ost.customers.dao.CustomerOrgDao;
import org.ost.customers.dao.CustomerProjectDao;
import org.ost.customers.dao.UserCustomersDao;
import org.ost.customers.dao.address.AddressDao;
import org.ost.customers.dao.contacinfo.ContactInfoDao;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.CustomerProject;
import org.ost.entity.customer.address.Address;
import org.ost.entity.customer.address.mapper.AddressEntityMapper;
import org.ost.entity.customer.contacts.ContactsInfo;
import org.ost.entity.customer.contacts.mapper.ContactInfoEntityMapper;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerProjectDto;
import org.ost.entity.customer.mapper.CustomerEntityMapper;
import org.ost.entity.customer.org.CustomerOrg;
import org.ost.entity.customer.user.UserCustomers;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.user.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
@Service
public class CustomerService {
	@Autowired
	ObjectMapper mapper;

	public static final String USER_NAME = "ost_user_name";
	public static final String TENANTID = "ost_tenatid";

	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private AddressDao addressDao;

	@Autowired
	private ContactInfoDao cInfoDao;

	@Autowired
	private UserCustomersDao userCustomerDao;
	@Autowired
	private CustomerOrgDao customerOrgDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public CustomerCreateVo createCustomer(String schemaId, CustomerCreateVo createVo) throws JsonProcessingException {
		// 新增客户
		Customer customer = new Customer();
		customer.setName(createVo.getName());
		customer.setCreateBy(createVo.getUserName());
		customer.setUpdateBy(customer.getCreateBy());

		customer.setCreateTime(new Date());
		customer.setUpdateTime(new Date());
		customer.setIsDelete(Short.valueOf("0"));
		customer.setParentId(createVo.getParentId());
		customer.setProperty(mapper.writeValueAsString(createVo.getProperty()));
		customer.setPy(Chinese2PY.getPinYin(customer.getName()));
		customer.setSzm(Chinese2PY.getSzm(customer.getName()));
		customer.setSchemaId(schemaId);
		this.customerDao.insertSelective(customer);
		// contactInfo
		List<ContactsInfo> cInfos = ContactInfoEntityMapper.INSTANCE
				.contactInfosVoToContactInfos(createVo.getContactInfos());
		// TODO insert list
		cInfos.forEach(cinfo -> {
			cinfo.setSchemaId(customer.getSchemaId());
			cinfo.setCreateBy(customer.getCreateBy());
			cinfo.setUpdateBy(customer.getUpdateBy());
			cinfo.setCustomerId(customer.getId());
			this.cInfoDao.insert(cinfo);
		});
		// addressInfo
		List<Address> addresses = AddressEntityMapper.INSTANCE.addressesInfoToAddresses(createVo.getAddress());
		addresses.forEach(address -> {
			address.setSchemaId(customer.getSchemaId());
			address.setCreateBy(customer.getCreateBy());
			address.setUpdateBy(customer.getUpdateBy());
			address.setCustomerId(customer.getId());
			this.addressDao.insert(address);
		});

		// dept owners

		createVo.getDeptOwners().forEach(deptOwner -> {
			CustomerOrg co = new CustomerOrg();
			co.setCreateBy(customer.getCreateBy());
			co.setUpdateBy(customer.getUpdateBy());
			co.setSchemaId(customer.getSchemaId());
			co.setCustomerId(customer.getId());
			co.setOrganizeId(Integer.valueOf(deptOwner.getId()));
			co.setOrganizeName(deptOwner.getName());
			customerOrgDao.insertSelective(co);
		});

		// manager owners
		createVo.getManagerOwners().forEach(managerOwner -> {
			UserCustomers uc = new UserCustomers();
			uc.setCreateBy(customer.getCreateBy());
			uc.setUpdateBy(customer.getUpdateBy());
			uc.setSchemaId(customer.getSchemaId());
			uc.setCustomerId(customer.getId());
			uc.setUserId(Integer.parseInt(managerOwner.getId()));
			uc.setUserName(managerOwner.getName());
			uc.setOrganizeId(Integer.parseInt(managerOwner.getDeptID()));
			uc.setOrganizeName(managerOwner.getDeptName());
			userCustomerDao.insertSelective(uc);
		});
		log.info("an new customer created");
		return createVo;
	}

	/**
	 * 
	 * @param params
	 * @return
	 * @throws JsonProcessingException
	 */
	@Transactional(readOnly = true)
	public PageEntity<CustomerListDto> queryCustomers(Customer customer, Integer curPage, Integer perPageSum) {
		Page page = new Page();
		page.setCurPage(curPage.intValue());
		page.setPerPageSum(perPageSum.intValue());
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		List<CustomerListDto> records = new ArrayList<CustomerListDto>();
		Map<String, String> params = null;
		if (customer.getProperty() != null) {
			params = (Map<String, String>) customer.getProperty();
		}
		Integer totalRecord = this.customerDao.selectCustomerCount(params, customer);
		if (totalRecord > 0) {
			List<Customer> customers = this.customerDao.selectCustomers(params, customer, rb);
			records = CustomerEntityMapper.INSTANCE.customersToCustomerListDtos(customers);

			int[] customerIds = customers.stream().mapToInt(c -> c.getId()).toArray();
			//

			List<CustomerOrg> cos = this.customerDao.selectCustomerOrg(customerIds);
			List<UserCustomers> ucs = this.customerDao.selectCustomerUsers(customerIds);
			records.forEach(record -> {

				record.setDeptOwner(CustomerEntityMapper.INSTANCE.CustomerOrgToDepartMentListDto(cos.stream()
						.filter(co -> co.getCustomerId().equals(record.getId())).collect(Collectors.toList())));
				;
				record.setManagerOwner(CustomerEntityMapper.INSTANCE.UserCustomerToUserListDto(ucs.stream()
						.filter(uc -> uc.getCustomerId().equals(record.getId())).collect(Collectors.toList())));
			});
		}
		PageEntity<CustomerListDto> p = new PageEntity<CustomerListDto>();
		p.setCurPage(curPage);
		p.setTotalRecord(totalRecord);
		p.setObjects(records);
		return p;
	}

	@Transactional(readOnly = true)
	public CustomerDetailDto queryDetail(Integer id, String schemaId) {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setIsDelete(Short.valueOf("0"));
		customer.setSchemaId(schemaId);
		Customer result = this.customerDao.selectOne(customer);
		if (result != null) {
			CustomerDetailDto detailDto = CustomerEntityMapper.INSTANCE.customerToCustomerDetailDto(result);
			Customer parent = null;
			if (result.getParentId() != null) {
				if ((parent = customerDao.selectByPrimaryKey(result.getParentId())) != null) {
					detailDto.setParent(new CustomerVo(result.getParentId(), parent.getName()));
				}
			}

			// phone
			ContactsInfo cInfo = new ContactsInfo();
			cInfo.setCustomerId(customer.getId());
			cInfo.setIsDelete(Short.parseShort("0"));
			cInfo.setSchemaId(schemaId);
			List<ContactsInfo> cinfos = cInfoDao.select(cInfo);
			detailDto.setPhone(ContactInfoEntityMapper.INSTANCE.contactsInfoToContactsInfoVo(cinfos));

			Address address = new Address();
			address.setSchemaId(schemaId);
			address.setCustomerId(customer.getId());
			address.setIsDelete(customer.getIsDelete());

			List<Address> addresses = addressDao.select(address);

			detailDto.setLocations(CustomerEntityMapper.INSTANCE.addressToAddressVo(addresses));
			// dept
			CustomerOrg co = new CustomerOrg();
			co.setCustomerId(customer.getId());
			co.setIsDelete(customer.getIsDelete());
			co.setSchemaId(customer.getSchemaId());
			List<CustomerOrg> cos = customerOrgDao.select(co);
			detailDto.setDeptOwner(CustomerEntityMapper.INSTANCE.CustomerOrgToDepartMentListDto(cos));

			UserCustomers uc = new UserCustomers();
			uc.setCustomerId(customer.getId());
			uc.setIsDelete(customer.getIsDelete());
			uc.setSchemaId(customer.getSchemaId());
			List<UserCustomers> ucs = userCustomerDao.select(uc);
			detailDto.setManagerOwner(CustomerEntityMapper.INSTANCE.UserCustomerToUserListDto(ucs));
			return detailDto;
		} else {
			return null;
		}
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateCustomer(Integer customerId, String schemaId, CustomerCreateVo updateVo)
			throws JsonProcessingException {
		// 修改客户
		Customer customer = new Customer();
		customer.setName(updateVo.getName());
		customer.setCreateBy(updateVo.getUserName());
		customer.setUpdateBy(customer.getCreateBy());
		customer.setId(customerId);
		customer.setCreateTime(new Date());
		customer.setUpdateTime(new Date());
		customer.setIsDelete(Short.valueOf("0"));
		customer.setParentId(updateVo.getParentId());
		customer.setProperty(mapper.writeValueAsString(updateVo.getProperty()));
		customer.setPy(Chinese2PY.getPinYin(customer.getName()));
		customer.setSzm(Chinese2PY.getSzm(customer.getName()));
		customer.setSchemaId(schemaId);
		this.customerDao.updateByPrimaryKeySelective(customer);
		// contactInfo
		if (CollectionUtils.isNotEmpty(updateVo.getContactInfos())) {
			// delete all

			this.customerDao.deleteContactInfo(customer.getId());

			List<ContactsInfo> cInfos = ContactInfoEntityMapper.INSTANCE
					.contactInfosVoToContactInfos(updateVo.getContactInfos());
			// TODO insert list
			cInfos.forEach(cinfo -> {
				cinfo.setSchemaId(customer.getSchemaId());
				cinfo.setCreateBy(customer.getCreateBy());
				cinfo.setUpdateBy(customer.getUpdateBy());
				cinfo.setCustomerId(customer.getId());
				this.cInfoDao.insert(cinfo);
			});

		}

		if (CollectionUtils.isNotEmpty(updateVo.getAddress())) {
			this.customerDao.deleteCustomerAddress(customer.getId());
			// addressInfo
			List<Address> addresses = AddressEntityMapper.INSTANCE.addressesInfoToAddresses(updateVo.getAddress());
			addresses.forEach(address -> {
				address.setSchemaId(customer.getSchemaId());
				address.setCreateBy(customer.getCreateBy());
				address.setUpdateBy(customer.getUpdateBy());
				address.setCustomerId(customer.getId());
				this.addressDao.insert(address);
			});
		}

		// dept owners

		if (CollectionUtils.isNotEmpty(updateVo.getDeptOwners())) {
			customerDao.deleteCustomerOrg(customer.getId());

			updateVo.getDeptOwners().forEach(deptOwner -> {
				CustomerOrg co = new CustomerOrg();
				co.setCreateBy(customer.getCreateBy());
				co.setUpdateBy(customer.getUpdateBy());
				co.setSchemaId(customer.getSchemaId());
				co.setCustomerId(customer.getId());
				co.setOrganizeId(Integer.valueOf(deptOwner.getId()));
				co.setOrganizeName(deptOwner.getName());
				customerOrgDao.insertSelective(co);
			});
		}

		if (CollectionUtils.isNotEmpty(updateVo.getManagerOwners())) {
			this.customerDao.deleteUserCustomers(customer.getId());
			// manager owners
			updateVo.getManagerOwners().forEach(managerOwner -> {
				UserCustomers uc = new UserCustomers();
				uc.setCreateBy(customer.getCreateBy());
				uc.setUpdateBy(customer.getUpdateBy());
				uc.setSchemaId(customer.getSchemaId());
				uc.setCustomerId(customer.getId());
				uc.setUserId(Integer.parseInt(managerOwner.getId()));
				uc.setUserName(managerOwner.getName());
				uc.setOrganizeId(Integer.parseInt(managerOwner.getDeptID()));
				uc.setOrganizeName(managerOwner.getDeptName());
				userCustomerDao.insertSelective(uc);
			});
		}
		log.info("an new customer created");
		return HttpStatus.OK.name();

	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public Integer deleteCustomer(Integer id, String schemaId, Users users) {
		Customer customer = new Customer();
		customer.setUpdateBy(users.getRealname());
		customer.setUpdateTime(new Date());
		customer.setIsDelete(Short.parseShort("1"));
		customer.setId(id);
		customer.setSchemaId(schemaId);
		return this.customerDao.updateByPrimaryKeySelective(customer);
	}

	@Transactional(readOnly = true)
	public CustomerListDto queryByContacts(String schemaID, Integer contactsID) {
		// CustomerContacts cc = new CustomerContacts();
		// cc.setContactID(contactsID);
		// cc.setSchemaId(schemaID);
		// if (CollectionUtils.isNotEmpty(ccs)) {
		// Customer customer =
		// this.customerDao.selectByPrimaryKey(ccs.get(0).getCustomerID());
		// return
		// CustomerEntityMapper.INSTANCE.customerToCustomerListDto(customer);
		// } else {
		// return null;
		// }

		return null;
	}

	@Transactional(readOnly = true)
	public List<CustomerListDto> queryByIds(String schemaID, Integer[] ids) {
		List<Customer> customers = this.customerDao.selectByIds(ids);
		return CustomerEntityMapper.INSTANCE.customersToCustomerListDtos(customers);
	}

	@Autowired
	private CustomerProjectDao cpDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String createCustomerProject(String schemaId, CustomerProjectDto dto) {
		CustomerProject cProject = new CustomerProject();
		cProject.setProjectID(dto.getProject().getId());
		cProject.setCustomerID(dto.getCustomer().getId());
		cProject.setCreateBy(dto.getUserName());
		cProject.setUpdateBy(dto.getUserName());
		cProject.setCreateTime(new Date());
		cProject.setUpdateTime(new Date());
		cProject.setSchemaId(schemaId);
		cpDao.insert(cProject);
		return HttpStatus.OK.name();
	}

}
