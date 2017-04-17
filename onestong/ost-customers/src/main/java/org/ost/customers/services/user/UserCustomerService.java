package org.ost.customers.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.common.tools.db.Page;
import org.ost.customers.dao.CustomerDao;
import org.ost.customers.dao.CustomerOrgDao;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
	@Autowired
	private CustomerOrgDao customerOrgDao;

	/**
	 * FIXME 客户关联的客户经理只需要查询一次
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

			if(CollectionUtils.isNotEmpty(records)){
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
		}
		PageEntity<CustomerListDto> p = new PageEntity<CustomerListDto>();
		p.setCurPage(page.getCurPage());
		p.setTotalRecord(totalRecord);
		p.setObjects(records);
		return p;
	}

	/**
	 * 多客户批量修改客户经理
	 * 
	 * @param accountName
	 *            当前操作人
	 * @param schemaID
	 *            多租户id
	 * @param customerListDtos
	 * @return
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public String updateUser(String accountName, String schemaID, List<CustomerListDto> customerListDtos) {
		//所属部门
		for (CustomerListDto customerListDto : customerListDtos) {
			this.customerDao.deleteCustomerOrg(customerListDto.getId());
			// manager owners
			customerListDto.getDeptOwner().forEach(deptOwner -> {
				CustomerOrg org = new CustomerOrg();
				org.setCreateBy(accountName);
				org.setUpdateBy(accountName);
				org.setSchemaId(schemaID);
				org.setCustomerId(customerListDto.getId());
				org.setOrganizeId(Integer.parseInt(deptOwner.getId()));
				org.setOrganizeName(deptOwner.getName());
				customerOrgDao.insertSelective(org);
			});
		}
		//所属人员
		for (CustomerListDto customerListDto : customerListDtos) {
			this.customerDao.deleteUserCustomers(customerListDto.getId());
			// manager owners
			customerListDto.getManagerOwner().forEach(managerOwner -> {
				UserCustomers uc = new UserCustomers();
				uc.setCreateBy(accountName);
				uc.setUpdateBy(accountName);
				uc.setSchemaId(schemaID);
				uc.setCustomerId(customerListDto.getId());
				uc.setUserId(Integer.parseInt(managerOwner.getId()));
				uc.setUserName(managerOwner.getName());
				uc.setOrganizeId(Integer.parseInt(managerOwner.getDeptID()));
				uc.setOrganizeName(managerOwner.getDeptName());
				userCustomerDao.insertSelective(uc);
			});
		}
		return HttpStatus.OK.name();
	}
}
