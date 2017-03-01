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

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.common.tools.OperateResult;
import org.common.tools.db.Page;
import org.ost.crm.client.CustomerServiceClient;
import org.ost.entity.base.PageEntity;
import org.ost.entity.customer.Customer;
import org.ost.entity.customer.address.vo.AddressVo;
import org.ost.entity.customer.contacts.vo.ContactInfoVo;
import org.ost.entity.customer.dto.CustomerDetailDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.mapper.CustomerEntityMapper;
import org.ost.entity.customer.vo.CustomerCreateVo;
import org.ost.entity.user.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("all")
@Service
public class CustomerService {
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private CustomerServiceClient customerServiceClient;

	public Object createCustomer(Users user, Map<String, Object> params) throws JsonProcessingException {
		// 地址
		List<Map<String, Object>> locations = (List<Map<String, Object>>) MapUtils.getObject(params, "locations");
		Map<String, Object> parentCustomer = MapUtils.getMap(params, "parent");
		List<Map<String, Object>> phones = (List<Map<String, Object>>) MapUtils.getObject(params, "phone");
		CustomerCreateVo vo = new CustomerCreateVo();
		// common arribute
		vo.setUserName(user.getRealname());
		vo.setSchemaId(user.getSchemaId());
		vo.setName(MapUtils.getString(params, "name"));
		vo.setParentId(MapUtils.getInteger(parentCustomer, "id"));
		List<AddressVo> addresses = new ArrayList<>();
		List<ContactInfoVo> contactInfos = new ArrayList<>();
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
		vo.setAddress(addresses);
		vo.setContactInfos(contactInfos);
		// dynamic
		params.remove("parent");
		params.remove("locations");
		params.remove("phone");
		params.remove("name");
		vo.setProperty(params);
		// invoke
		this.customerServiceClient.createCustomer(user.getSchemaId(),vo);
		return params;
	}

	public Object queryCustomers(Users current, Map<String, String> params, Page page) {
		Customer customer = new Customer();
		customer.setSchemaId(current.getSchemaId());
		String name = null;
		if ((name = MapUtils.getString(params, "name")) != null) {
			customer.setName(name);
		}
		customer.setProperty(params);
		PageEntity<CustomerListDto> result = this.customerServiceClient.queryMember(current.getSchemaId(),page.getCurPage(),
				page.getPerPageSum(), customer);
		List<CustomerListDto> customers = result.getObjects();
		final List<Map<String, Object>> tmps = new ArrayList<Map<String, Object>>();
		customers.forEach(c -> {
			Map<String, Object> _t = transBean2Map(c);
			_t.putAll(c.getProperties());
			tmps.add(_t);
		});
		PageEntity<Map<String, Object>> _result = new PageEntity<Map<String, Object>>();
		_result.setCurPage(result.getCurPage());
		_result.setTotalRecord(result.getTotalRecord());
		_result.setObjects(tmps);
		return _result;
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

	public Object queryConditions() {
		return null;
	}

	public CustomerDetailDto queryDetail(Integer customerId, Users user) {
		OperateResult<CustomerDetailDto>  result = this.customerServiceClient.queryDetail(customerId, user.getSchemaId());
		return result.getData();
	}
}
