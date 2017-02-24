package org.ost.entity.customer.vo;

import java.util.List;
import java.util.Map;

import org.ost.entity.customer.address.vo.AddressVo;
import org.ost.entity.customer.contacts.vo.ContactInfoVo;

public class CustomerCreateVo {

	private String tenantId;
	private String userName;

	private String name;
	private Integer parentId;
	private Map<String, Object> property;

	private List<AddressVo> address;

	private List<ContactInfoVo> contactInfos;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Map<String, Object> getProperty() {
		return property;
	}

	public void setProperty(Map<String, Object> property) {
		this.property = property;
	}

	public List<AddressVo> getAddress() {
		return address;
	}

	public void setAddress(List<AddressVo> address) {
		this.address = address;
	}

	public List<ContactInfoVo> getContactInfos() {
		return contactInfos;
	}

	public void setContactInfos(List<ContactInfoVo> contactInfos) {
		this.contactInfos = contactInfos;
	}

}
