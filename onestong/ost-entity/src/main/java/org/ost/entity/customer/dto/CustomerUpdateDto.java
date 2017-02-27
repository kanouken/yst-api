package org.ost.entity.customer.dto;

import java.util.List;

import org.ost.entity.contacts.dto.ContactsDto;

public class CustomerUpdateDto {
	private Integer id;
	private String updateTime;
	private String updateBy;

	private String name;
	private String py;
	private String szm;
	private Integer parentId;
	private Object property;

	private List<ContactsDto> contacts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Object getProperty() {
		return property;
	}

	public void setProperty(Object property) {
		this.property = property;
	}

	public List<ContactsDto> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactsDto> contacts) {
		this.contacts = contacts;
	}

}
