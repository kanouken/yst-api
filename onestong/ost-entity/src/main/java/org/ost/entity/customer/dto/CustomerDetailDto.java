package org.ost.entity.customer.dto;

import java.util.List;
import java.util.Map;

import org.ost.entity.customer.address.vo.AddressVo;
import org.ost.entity.customer.contacts.vo.ContactInfoVo;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.org.department.dto.DepartmentListDto;
import org.ost.entity.user.dto.UserListDto;

public class CustomerDetailDto {
	private Integer id;
	private String name;
	private String py;
	private String szm;
	private CustomerVo parent;

	private List<ContactInfoVo> phone;

	private List<DepartmentListDto> deptOwner;

	private List<AddressVo> locations;

	private List<UserListDto> managerOwner;

	private Map<String, Object> properties;
	private String createTime;

	public List<AddressVo> getLocations() {
		return locations;
	}

	public void setLocations(List<AddressVo> locations) {
		this.locations = locations;
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

	public CustomerVo getParent() {
		return parent;
	}

	public void setParent(CustomerVo parent) {
		this.parent = parent;
	}

	public List<ContactInfoVo> getPhone() {
		return phone;
	}

	public void setPhone(List<ContactInfoVo> phone) {
		this.phone = phone;
	}

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

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
