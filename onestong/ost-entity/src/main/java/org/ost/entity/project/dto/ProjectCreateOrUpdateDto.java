package org.ost.entity.project.dto;

import java.util.List;

import org.ost.entity.contacts.dto.ContactsDto;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.org.department.dto.DeptCreateOrUpdateDto;
import org.ost.entity.user.dto.UserCreateOrUpdateDto;

public class ProjectCreateOrUpdateDto {

	private String createBy;
	private String updateBy;

	private String amount;
	private List<ContactsDto> contacts;
	private CustomerVo customer;
	private String cyc;

	private List<DeptCreateOrUpdateDto> deptOwners;

	private String details;
	private String id;

	private String isCyc;
	private List<UserCreateOrUpdateDto> managerOwners;
	private String name;
	private String startTime;
	private String startTimeStr;
	private Integer typeID;
	private String typeName;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public List<ContactsDto> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactsDto> contacts) {
		this.contacts = contacts;
	}

	public CustomerVo getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerVo customer) {
		this.customer = customer;
	}

	public String getCyc() {
		return cyc;
	}

	public void setCyc(String cyc) {
		this.cyc = cyc;
	}

	public List<DeptCreateOrUpdateDto> getDeptOwners() {
		return deptOwners;
	}

	public void setDeptOwners(List<DeptCreateOrUpdateDto> deptOwners) {
		this.deptOwners = deptOwners;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsCyc() {
		return isCyc;
	}

	public void setIsCyc(String isCyc) {
		this.isCyc = isCyc;
	}

	public List<UserCreateOrUpdateDto> getManagerOwners() {
		return managerOwners;
	}

	public void setManagerOwners(List<UserCreateOrUpdateDto> managerOwners) {
		this.managerOwners = managerOwners;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public Integer getTypeID() {
		return typeID;
	}

	public void setTypeID(Integer typeID) {
		this.typeID = typeID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
