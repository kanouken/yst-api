package org.ost.crm.services.customer;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.common.tools.exception.ApiException;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.crm.model.common.CommonParams;
import org.ost.crm.services.base.BaseService;
import org.ost.crm.services.visit.VisitService;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.address.vo.AddressVo;
import org.ost.entity.customer.contacts.vo.ContactInfoVo;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.dto.CustomerQueryDto;
import org.ost.entity.customer.mapper.CustomerEntityMapper;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.org.department.dto.DepartmentListDto;
import org.ost.entity.user.Users;
import org.ost.entity.user.dto.UserListDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("all")
@Service
public class CustomerService extends BaseService {
	
	@Autowired
	VisitService  visitService;
	
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private CustomerServiceClient customerServiceClient;

	public String createCustomer(Users user, Map<String, Object> params) throws JsonProcessingException {
		// 地址
		List<Map<String, Object>> locations = (List<Map<String, Object>>) MapUtils.getObject(params, "locations");
		Map<String, Object> parentCustomer = MapUtils.getMap(params, "parent");
		List<Map<String, Object>> phones = (List<Map<String, Object>>) MapUtils.getObject(params, "phone");

		List<Map<String, Object>> deptOwners = (List<Map<String, Object>>) MapUtils.getObject(params, "deptOwner");
		List<Map<String, Object>> managerOwners = (List<Map<String, Object>>) MapUtils.getObject(params,
				"managerOwner");

		CustomerCreateVo vo = new CustomerCreateVo();
		// common arribute
		vo.setUserName(user.getRealname());
		vo.setName(MapUtils.getString(params, "name"));
		vo.setParentId(MapUtils.getInteger(parentCustomer, "id"));
		List<AddressVo> addresses = new ArrayList<>();
		List<ContactInfoVo> contactInfos = new ArrayList<>();

		List<DepartmentListDto> deptownersList = new ArrayList<DepartmentListDto>();
		List<UserListDto> managerOwnersList = new ArrayList<UserListDto>();
		locations.forEach(location -> {
			AddressVo _address = new AddressVo();
			this.transMap2Bean(location, _address);
			addresses.add(_address);
		});
		phones.forEach(contactInfo -> {
			ContactInfoVo _contactInfo = new ContactInfoVo();
			this.transMap2Bean(contactInfo, _contactInfo);
			contactInfos.add(_contactInfo);
		});

		deptOwners.forEach(deptOwner -> {
			DepartmentListDto _deptOwner = new DepartmentListDto();
			this.transMap2Bean(deptOwner, _deptOwner);
			deptownersList.add(_deptOwner);
		});

		managerOwners.forEach(managerOwner -> {
			UserListDto _userOwner = new UserListDto();
			this.transMap2Bean(managerOwner, _userOwner);
			managerOwnersList.add(_userOwner);
		});

		vo.setAddress(addresses);
		vo.setContactInfos(contactInfos);
		// 所属部门

		vo.setDeptOwners(deptownersList);
		vo.setManagerOwners(managerOwnersList);
		// dynamic
		params.remove("parent");
		params.remove("locations");
		params.remove("phone");
		params.remove("name");
		params.remove("deptOwner");
		params.remove("managerOwner");
		vo.setProperty(params);
		// invoke
		OperateResult<CustomerCreateVo> result = this.customerServiceClient.createCustomer(user.getSchemaId(), vo);
		if (result.success()) {
			return "ok";
		} else {
			throw new ApiException("新增客户失败", result.getInnerException());
		}

	}

	/**
	 * ✅普通用户只能查看属于自己的客户 
	 * TODO 部门主管可以看本部门以及下级部门的所有客户
	 * 
	 * @param current
	 * @param params
	 * @param page
	 * @return
	 */
	public Map<String, Object> queryCustomersByUserScope(Users current, Map<String, String> params, Page page) {
		Boolean isDirector = current.getIsDirector().equals(Byte.parseByte("0")) ? false : true;
		CustomerQueryDto customerQueryDto = new CustomerQueryDto();
		customerQueryDto.setSchemaId(current.getSchemaId());
		String name = null;
		if ((name = MapUtils.getString(params, "name")) != null) {
			customerQueryDto.setName(name);
			params.remove("name");
		}
		customerQueryDto.setProperty(params);
		OperateResult<PageEntity<CustomerListDto>> result = null;
		if (isDirector) {
			// 按部门搜索
			
		} else {
			result = this.customerServiceClient.queryCustomerByUser(current.getSchemaId(), page.getCurPage(),
					page.getPerPageSum(), customerQueryDto);
		}
		if (result.success()) {
			List<CustomerListDto> customers = result.getData().getObjects();
			final List<Map<String, Object>> tmps = new ArrayList<Map<String, Object>>();
			customers.forEach(c -> {
				Map<String, Object> _t = transBean2Map(c);
				_t.putAll(c.getProperties());
				_t.remove("properties");
				tmps.add(_t);
			});
			page.setTotalRecords(result.getData().getTotalRecord());
			return OperateResult.renderPage(page, tmps);
		} else {
			throw new ApiException("获取列表失败", result.getInnerException());
		}
	}

	/**
	 * 普通用户只能查看属于自己的客户 部门主管可以看本部门以及下级部门的所有客户
	 * 
	 * @see #queryCustomersByUserScope(Users, Map, Page)
	 * @param current
	 * @param params
	 * @param page
	 * @return
	 */
	@Deprecated
	public Map<String, Object> queryCustomers(Users current, Map<String, String> params, Page page) {
		Customer customer = new Customer();
		customer.setSchemaId(current.getSchemaId());
		String name = null;
		if ((name = MapUtils.getString(params, "name")) != null) {
			customer.setName(name);
			params.remove("name");
		}
		customer.setProperty(params);
		OperateResult<PageEntity<CustomerListDto>> result = this.customerServiceClient
				.queryMember(current.getSchemaId(), page.getCurPage(), page.getPerPageSum(), customer);
		if (result.success()) {
			List<CustomerListDto> customers = result.getData().getObjects();
			final List<Map<String, Object>> tmps = new ArrayList<Map<String, Object>>();
			customers.forEach(c -> {
				Map<String, Object> _t = transBean2Map(c);
				_t.putAll(c.getProperties());
				_t.remove("properties");
				tmps.add(_t);
			});
			page.setTotalRecords(result.getData().getTotalRecord());
			return OperateResult.renderPage(page, tmps);
		} else {

			throw new ApiException("获取列表失败", result.getInnerException());
		}
	}

	private static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (!key.equals("class")) {
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}

	public static void transMap2Bean(Map<String, Object> map, Object obj) {

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}

			}

		} catch (Exception e) {
			System.out.println("transMap2Bean Error " + e);
		}

		return;

	}

	// TODO cache
	@Transactional(readOnly = true)
	public Map<String, Object> queryConditions() {
		List<CommonParams> belongIndustry = this.getParams("customer_belongIndustry");
		List<CommonParams> dealFrequency = this.getParams("customer_dealFrequency");
		List<CommonParams> isPlc = this.getParams("customer_isPlc");
		List<CommonParams> natrue = this.getParams("customer_natrue");
		List<CommonParams> source = this.getParams("customer_source");
		List<CommonParams> turnover = this.getParams("customer_turnover");
		List<CommonParams> type = this.getParams("customer_type");
		List<CommonParams> scale = this.getParams("customer_scale");

		Map<String, Object> reqMap = new HashMap<String, Object>();

		reqMap.put("belongIndustry", belongIndustry.stream().map(b -> b.getVal()).collect(Collectors.toList()));
		reqMap.put("dealFrequency", dealFrequency.stream().map(b -> b.getVal()).collect(Collectors.toList()));
		reqMap.put("isPlc", isPlc.stream().map(b -> b.getVal()).collect(Collectors.toList()));
		reqMap.put("natrue", natrue.stream().map(b -> b.getVal()).collect(Collectors.toList()));
		reqMap.put("source", source.stream().map(b -> b.getVal()).collect(Collectors.toList()));
		reqMap.put("turnover", turnover.stream().map(b -> b.getVal()).collect(Collectors.toList()));
		reqMap.put("type", type.stream().map(b -> b.getVal()).collect(Collectors.toList()));
		reqMap.put("scale", scale.stream().map(b -> b.getVal()).collect(Collectors.toList()));

		return reqMap;
	}

	public Map<String, Object> queryDetail(Integer customerId, Users user) {
		OperateResult<CustomerDetailDto> result = this.customerServiceClient.queryDetail(customerId,
				user.getSchemaId());

		if (result.success()) {
			CustomerDetailDto detailDto = result.getData();

			Map<String, Object> tmp = this.transBean2Map(detailDto);
			tmp.remove("properties");
			tmp.putAll(detailDto.getProperties());
			return tmp;
		} else {
			throw new ApiException("获取用户详细失败", result.getInnerException());
		}

	}

	public String deleteCustomer(Users user, Integer customerId) {
		OperateResult<Integer> result = customerServiceClient.deleteCustomer(customerId, user.getSchemaId(), user);
		if (result.success()) {
			return HttpStatus.OK.name();
		} else {
			throw new ApiException("获取用户详细失败", result.getInnerException());
		}

	}

	public String updateCustomer(Integer customerId, Map<String, Object> params, Users user) {
		// 地址
		List<Map<String, Object>> locations = (List<Map<String, Object>>) MapUtils.getObject(params, "locations");
		Map<String, Object> parentCustomer = MapUtils.getMap(params, "parent");
		List<Map<String, Object>> phones = (List<Map<String, Object>>) MapUtils.getObject(params, "phone");

		List<Map<String, Object>> deptOwners = (List<Map<String, Object>>) MapUtils.getObject(params, "deptOwner");
		List<Map<String, Object>> managerOwners = (List<Map<String, Object>>) MapUtils.getObject(params,
				"managerOwner");

		CustomerCreateVo vo = new CustomerCreateVo();
		vo.setId(customerId);
		// common arribute
		vo.setUserName(user.getRealname());
		vo.setName(MapUtils.getString(params, "name"));
		vo.setParentId(MapUtils.getInteger(parentCustomer, "id"));
		List<AddressVo> addresses = new ArrayList<>();
		List<ContactInfoVo> contactInfos = new ArrayList<>();

		List<DepartmentListDto> deptownersList = new ArrayList<DepartmentListDto>();
		List<UserListDto> managerOwnersList = new ArrayList<UserListDto>();
		locations.forEach(location -> {
			AddressVo _address = new AddressVo();
			this.transMap2Bean(location, _address);
			addresses.add(_address);
		});
		phones.forEach(contactInfo -> {
			ContactInfoVo _contactInfo = new ContactInfoVo();
			this.transMap2Bean(contactInfo, _contactInfo);
			contactInfos.add(_contactInfo);
		});

		deptOwners.forEach(deptOwner -> {
			DepartmentListDto _deptOwner = new DepartmentListDto();
			this.transMap2Bean(deptOwner, _deptOwner);
			deptownersList.add(_deptOwner);
		});

		managerOwners.forEach(managerOwner -> {
			UserListDto _userOwner = new UserListDto();
			this.transMap2Bean(managerOwner, _userOwner);
			managerOwnersList.add(_userOwner);
		});

		vo.setAddress(addresses);
		vo.setContactInfos(contactInfos);
		// 所属部门

		vo.setDeptOwners(deptownersList);
		vo.setManagerOwners(managerOwnersList);
		// dynamic
		params.remove("parent");
		params.remove("locations");
		params.remove("phone");
		params.remove("name");
		params.remove("deptOwner");
		params.remove("managerOwner");
		vo.setProperty(params);
		// invoke
		OperateResult<String> result = this.customerServiceClient.updateCustomer(customerId, user.getSchemaId(), vo);
		if (result.success()) {
			return HttpStatus.OK.name();
		} else {
			throw new ApiException("修改客户失败", result.getInnerException());
		}

	}

	/**
	 * 批量分配客户经理 部门
	 * 
	 * @param user
	 * @param dtos
	 * @return
	 */
	public String updateMangerOwnersBatch(Users user, List<CustomerListDto> dtos) {

		return null;
	}

	/**
	 * 
	 * @param users
	 * @param customerId
	 * @param createBy 
	 * @param contactDate 
	 * @param contactType 
	 * @param page 
	 * @return
	 */
	public Map<String,Object> queryVisits(Users users, Integer customerId, String contactType, String contactDate, String createBy, Page page) {
		Customer customer  = new Customer();
		customer.setId(customerId);
		customer.setSchemaId(users.getSchemaId());
		return  visitService.queryByCustomer(customer,contactDate,contactType,createBy,page);
	}
}
