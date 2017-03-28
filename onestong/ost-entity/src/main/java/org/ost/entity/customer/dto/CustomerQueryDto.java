package org.ost.entity.customer.dto;

import java.util.List;

public class CustomerQueryDto {
	private String schemaId;

	private String name;
	private String py;
	private String szm;
	private Integer parentId;
	private Object property;

	private Integer userId;

	private List<Integer> deptIds;

	public String getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Integer> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<Integer> deptIds) {
		this.deptIds = deptIds;
	}

}
