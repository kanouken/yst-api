package org.ost.entity.project.dto;

import java.util.List;

import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.org.department.dto.DepartmentListDto;
import org.ost.entity.user.dto.UserListDto;

public class ProjectDetailDto {

	private String amount;
	private List<ContactsListDto> contacts;

	private CustomerVo customer;
	private String cyc;
	private List<DepartmentListDto> deptOwner;

	private String details;
	private Integer id;
	private String isCyc;
	private List<UserListDto> managerOwner;
	private String name;
	private List<ProjectPaymentDto> payment;
	private String startTimeStr;
	private String state;
	private String stateName;
	private List<ProjectStepsDto> steps;
	private String typeID;
	private String typeName;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public List<ContactsListDto> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactsListDto> contacts) {
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

	public List<DepartmentListDto> getDeptOwner() {
		return deptOwner;
	}

	public void setDeptOwner(List<DepartmentListDto> deptOwner) {
		this.deptOwner = deptOwner;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsCyc() {
		return isCyc;
	}

	public void setIsCyc(String isCyc) {
		this.isCyc = isCyc;
	}

	public List<UserListDto> getManagerOwner() {
		return managerOwner;
	}

	public void setManagerOwner(List<UserListDto> managerOwner) {
		this.managerOwner = managerOwner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProjectPaymentDto> getPayment() {
		return payment;
	}

	public void setPayment(List<ProjectPaymentDto> payment) {
		this.payment = payment;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<ProjectStepsDto> getSteps() {
		return steps;
	}

	public void setSteps(List<ProjectStepsDto> steps) {
		this.steps = steps;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
