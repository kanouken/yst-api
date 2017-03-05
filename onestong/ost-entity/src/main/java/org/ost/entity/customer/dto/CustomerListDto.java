package org.ost.entity.customer.dto;

import java.util.List;
import java.util.Map;

import org.ost.entity.org.department.dto.DepartmentListDto;
import org.ost.entity.user.dto.UserListDto;

public class CustomerListDto {
	private Integer id;
	private String name;
	private String py;
	private String szm;

	private List<DepartmentListDto> deptOwner;
	
	private List<UserListDto> managerOwner;
	
	
	
	public List<DepartmentListDto> getDeptOwner() {
		return deptOwner;
	}

	public void setDeptOwner(List<DepartmentListDto> deptOwner) {
		this.deptOwner = deptOwner;
	}

	public List<UserListDto> getManagerOwner() {
		return managerOwner;
	}

	public void setManagerOwner(List<UserListDto> managerOwner) {
		this.managerOwner = managerOwner;
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

	private Map<String, Object> properties;

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

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

}
