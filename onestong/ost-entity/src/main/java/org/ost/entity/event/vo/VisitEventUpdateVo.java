package org.ost.entity.event.vo;

import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.location.vo.LocationVo;
import org.ost.entity.project.vo.ProjectVo;

public class VisitEventUpdateVo {
	private String eId;
	private String contactType;
	private String contactTime;
	private String content;

	private String busChange;

	private CustomerVo customer;

	private ProjectVo project;

	private LocationVo location;

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContactTime() {
		return contactTime;
	}

	public void setContactTime(String contactTime) {
		this.contactTime = contactTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBusChange() {
		return busChange;
	}

	public void setBusChange(String busChange) {
		this.busChange = busChange;
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

	public LocationVo getLocation() {
		return location;
	}

	public void setLocation(LocationVo location) {
		this.location = location;
	}

}
