package org.ost.customers.services;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.ost.customers.dao.CustomerDao;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.mapper.CustomerEntityMapper;
import org.ost.entity.user.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class CustomerService {

	public static final String USER_NAME = "ost_user_name";
	public static final String TENANTID = "ost_tenatid";

	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CustomerDao customerDao;

	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void createCustomer(Customer customer) {
		this.customerDao.insert(customer);
		log.info("an new customer created");
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	@Transactional(readOnly = true)
	public Object queryCustomers(Map<String, String> params, Integer curPage, Integer perPageSum) {
		Assert.notEmpty(params, "params can not null");
		Users currentUser = getCurrentUser(params);
		Page page = new Page();
		page.setCurPage(curPage.intValue());
		page.setPerPageSum(perPageSum.intValue());
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		List<Customer> customers = this.customerDao.selectCustomers(params, currentUser.getTenatId(), rb);
		return CustomerEntityMapper.INSTANCE.customersToCustomerListDtos(customers);
	}

	private Users getCurrentUser(Map<String, String> params) {
		String username = MapUtils.getString(params, USER_NAME);
		String tenantId = MapUtils.getString(params, TENANTID);
		Users u = new Users();
		u.setRealname(username);
		u.setTenatId(tenantId);
		params.remove(USER_NAME);
		params.remove(TENANTID);
		return u;
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
