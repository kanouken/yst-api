package org.ost.customers.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.ost.customers.dao.CustomerDao;
import org.ost.customers.dao.address.AddressDao;
import org.ost.customers.dao.contacinfo.ContactInfoDao;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.address.Address;
import org.ost.entity.customer.address.mapper.AddressEntityMapper;
import org.ost.entity.customer.contacts.ContactInfo;
import org.ost.entity.customer.contacts.mapper.ContactInfoEntityMapper;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.mapper.CustomerEntityMapper;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sourceforge.pinyin4j.PinyinHelper;

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
//		customer.setPy(PinyinHelper.);
		customer.setSzm("sfsf");
		customer.setTenantId(createVo.getTenantId());
		Integer customerId = this.customerDao.insert(customer);
		// contactInfo
		List<ContactInfo> cInfos = ContactInfoEntityMapper.INSTANCE
				.contactInfosVoToContactInfos(createVo.getContactInfos());
		cInfos.forEach(cinfo -> {
			cinfo.setTenantId(customer.getTenantId());
			cinfo.setCreateBy(customer.getCreateBy());
			cinfo.setUpdateBy(customer.getUpdateBy());
			cinfo.setCustomerId(customerId);
			this.cInfoDao.insert(cinfo);
		});
		// addressInfo
		List<Address> addresses = AddressEntityMapper.INSTANCE.addressesInfoToAddresses(createVo.getAddress());
		addresses.forEach(address -> {
			address.setTenantId(customer.getTenantId());
			address.setCreateBy(customer.getCreateBy());
			address.setUpdateBy(customer.getUpdateBy());
			address.setCustomerId(customerId);
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
	public Object queryDetail(Integer id, String tenantId) {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setIsDelete(Short.valueOf("0"));
		customer.setTenantId(tenantId);
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
}
