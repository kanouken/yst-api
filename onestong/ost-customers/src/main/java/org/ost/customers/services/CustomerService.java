package org.ost.customers.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.common.tools.pinyin.Chinese2PY;
import org.ost.customers.dao.CustomerDao;
import org.ost.customers.dao.address.AddressDao;
import org.ost.customers.dao.contacinfo.ContactInfoDao;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.address.Address;
import org.ost.entity.customer.address.mapper.AddressEntityMapper;
import org.ost.entity.customer.contacts.ContactsInfo;
import org.ost.entity.customer.contacts.mapper.ContactInfoEntityMapper;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.mapper.CustomerEntityMapper;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.user.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

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

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createCustomer(CustomerCreateVo createVo) throws JsonProcessingException {

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
		customer.setSchemaId(createVo.getSchemaId());
		this.customerDao.insert(customer);
		// contactInfo
		List<ContactsInfo> cInfos = ContactInfoEntityMapper.INSTANCE
				.contactInfosVoToContactInfos(createVo.getContactInfos());
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
		log.info("an new customer created");
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

		}
		PageEntity<CustomerListDto> p = new PageEntity<CustomerListDto>();
		p.setCurPage(curPage);
		p.setTotalRecord(totalRecord);
		p.setObjects(records);
		return p;
	}

	@Transactional(readOnly = true)
	public Object queryDetail(Integer id, String schemaId) {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setIsDelete(Short.valueOf("0"));
		customer.setSchemaId(schemaId);
		Customer result = this.customerDao.selectOne(customer);
		if (result != null) {
			return CustomerEntityMapper.INSTANCE.customerToCustomerDetailDto(result);
		} else {
			return null;
		}
	}

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateCustomer(Customer customer) {
		this.customerDao.updateCustomerSelective(customer);
	}
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void deleteCustomer(Integer id, String schemaId, Users users) {
		Customer customer  =  new Customer();
		customer.setUpdateBy(users.getRealname());
		customer.setUpdateTime(new Date());
		customer.setIsDelete(Short.parseShort("1"));
		customer.setId(id);
		customer.setSchemaId(schemaId);
		this.customerDao.updateByPrimaryKeySelective(customer);
	}

}
