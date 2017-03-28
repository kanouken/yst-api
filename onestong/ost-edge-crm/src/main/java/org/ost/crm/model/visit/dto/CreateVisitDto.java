package org.ost.crm.model.visit.dto;

import java.util.List;

import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.project.dto.ProjectListDto;
import org.ost.entity.user.dto.UserListDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class CreateVisitDto {
	/**
	 * 审批人员
	 */
	@ApiModelProperty("审批人员")
	private List<UserListDto> approvalUsers;
	private String city;
	/**
	 * 联系人
	 */
	private List<ContactsListDto> contacts;
	private String customerID;
	private String customerName;
	private String detailAddress1;
	private String district;
	private String lat;
	private String lng;
	/**
	 * 商机 》》 项目类别
	 */
	private String projectTypeID;
	/**
	 * 关联项目
	 */
	private List<ProjectListDto> projects;

	private String province;
	/**
	 * 支持人员
	 */
	private List<UserListDto> supportUsers;
	private List<String> visitContent;
	private String visitDetail;
	private String visitTime;
	private String visitType;

	public List<UserListDto> getApprovalUsers() {
		return approvalUsers;
	}

	public void setApprovalUsers(List<UserListDto> approvalUsers) {
		this.approvalUsers = approvalUsers;
	}

	public List<ContactsListDto> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactsListDto> contacts) {
		this.contacts = contacts;
	}

	public String getProjectTypeID() {
		return projectTypeID;
	}

	public void setProjectTypeID(String projectTypeID) {
		this.projectTypeID = projectTypeID;
	}

	public List<ProjectListDto> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectListDto> projects) {
		this.projects = projects;
	}

	public List<UserListDto> getSupportUsers() {
		return supportUsers;
	}

	public void setSupportUsers(List<UserListDto> supportUsers) {
		this.supportUsers = supportUsers;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDetailAddress1() {
		return detailAddress1;
	}

	public void setDetailAddress1(String detailAddress1) {
		this.detailAddress1 = detailAddress1;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public List<String> getVisitContent() {
		return visitContent;
	}

	public void setVisitContent(List<String> visitContent) {
		this.visitContent = visitContent;
	}

	public String getVisitDetail() {
		return visitDetail;
	}

	public void setVisitDetail(String visitDetail) {
		this.visitDetail = visitDetail;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

}
