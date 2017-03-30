package org.ost.customers.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.ost.customers.dao.CustomerDao;
import org.ost.customers.dao.UserCustomersDao;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerQueryDto;
import org.ost.entity.customer.mapper.CustomerEntityMapper;
import org.ost.entity.customer.org.CustomerOrg;
import org.ost.entity.customer.user.UserCustomers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings({ "unused", "unchecked" })
@Service
public class UserCustomerService {
	@Autowired
	ObjectMapper mapper;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private UserCustomersDao userCustomerDao;

	/**
	 * 
	 * @param params
	 * @return
	 * @throws JsonProcessingException
	 */
	@Transactional(readOnly = true)
	public PageEntity<CustomerListDto> queryCustomerByUser(CustomerQueryDto customer, Page page) {
		RowBounds rb = new RowBounds(page.getNextPage(), page.getPerPageSum());
		List<CustomerListDto> records = new ArrayList<CustomerListDto>();
		Map<String, String> params = null;
		String keyword = null;
		if (customer.getProperty() != null) {
			params = (Map<String, String>) customer.getProperty();
			keyword = MapUtils.getString(params, "keyword");
			if (StringUtils.isNotEmpty(keyword)) {
				params.remove("keyword");
			}
		}
		Integer totalRecord = this.userCustomerDao.selectCustomerCount(params, customer, keyword);
		if (totalRecord > 0) {
			List<Customer> customers = this.userCustomerDao.selectCustomers(params, customer, keyword, rb);
			records = CustomerEntityMapper.INSTANCE.customersToCustomerListDtos(customers);

			int[] customerIds = customers.stream().mapToInt(c -> c.getId()).toArray();
			List<CustomerOrg> cos = this.customerDao.selectCustomerOrg(customerIds);
			List<UserCustomers> ucs = this.customerDao.selectCustomerUsers(customerIds);
			records.forEach(record -> {
				record.setDeptOwner(CustomerEntityMapper.INSTANCE.CustomerOrgToDepartMentListDto(cos.stream()
						.filter(co -> co.getCustomerId().equals(record.getId())).collect(Collectors.toList())));
				record.setManagerOwner(CustomerEntityMapper.INSTANCE.UserCustomerToUserListDto(ucs.stream()
						.filter(uc -> uc.getCustomerId().equals(record.getId())).collect(Collectors.toList())));
			});
		}
		PageEntity<CustomerListDto> p = new PageEntity<CustomerListDto>();
		p.setCurPage(page.getCurPage());
		p.setTotalRecord(totalRecord);
		p.setObjects(records);
		return p;
	}
}