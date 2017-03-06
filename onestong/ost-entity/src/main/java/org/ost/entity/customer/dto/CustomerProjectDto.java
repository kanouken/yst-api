package org.ost.entity.customer.dto;

import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.project.vo.ProjectVo;

public class CustomerProjectDto {

	private String userName;
	private CustomerVo customer;

	private ProjectVo project;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public CustomerVo getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerVo customer) {
		this.customer = customer;
	}

	public ProjectVo getProject() {
		return project;
	}

	public void setProject(ProjectVo project) {
		this.project = project;
	}

}
