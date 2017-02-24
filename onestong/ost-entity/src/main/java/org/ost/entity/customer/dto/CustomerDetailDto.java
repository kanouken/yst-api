package org.ost.entity.customer.dto;

import java.util.List;
import java.util.Map;

import org.ost.entity.contacts.dto.ContactsDetailDto;

public class CustomerDetailDto {
	private Integer id;
	private String name;
	private String py;
	private String szm;
	private CustomerDetailDto parentCustomer;

	private List<ContactsDetailDto> contacts;

	private Map<String, Object> properties;
	private String createTimeStr;

	public List<ContactsDetailDto> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactsDetailDto> contacts) {
		this.contacts = contacts;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getSzm() {
		return szm;
	}

	public void setSzm(String szm) {
		this.szm = szm;
	}

	public CustomerDetailDto getParentCustomer() {
		return parentCustomer;
	}

	public void setParentCustomer(CustomerDetailDto parentCustomer) {
		this.parentCustomer = parentCustomer;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

}
