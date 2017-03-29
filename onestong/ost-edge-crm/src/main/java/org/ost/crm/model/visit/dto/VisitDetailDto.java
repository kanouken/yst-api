package org.ost.crm.model.visit.dto;

import java.util.Date;
import java.util.List;

import org.ost.crm.model.visit.VisitApprovalFlow;
import org.ost.crm.services.visit.VisitService;

import io.swagger.annotations.ApiModelProperty;

public class VisitDetailDto {

	@ApiModelProperty(value = "审批状态 -1:审批未通过 0:审批未完成 1:审批通过")
	private String approvalStatus;
	@ApiModelProperty(value = "审批状态描述")
	private String approvalStatusName;
	private String city;
	private Date createTime;
	private String createTimeStr;
	private String customerID;
	private String customerName;
	private String detailAddress1;
	private String district;
	private String id;
	private String lat;
	private String lng;
	private String projectTypeID;
	private String province;
	@ApiModelProperty(value = "角色	string	@mock=发起人")
	private String role;
	@ApiModelProperty(value = "当前审批用户审批状态 -1:审批未通过 0:审批未完成 1:审批通过	number	@mock=0")
	private String roleApprovalStatus;
	private String userID;
	private String userName;
	private List visitContent;
	private String visitDetail;
	private Date visitTime;
	private String visitTimeStr;
	private String visitType;

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalStatusName() {
		if (approvalStatus != null) {
			setApprovalStatusName(VisitApprovalFlow.getApprovalFlow(Byte.parseByte(approvalStatus)).getStateName());
		}
		return approvalStatusName;
	}

	public void setApprovalStatusName(String approvalStatusName) {
		this.approvalStatusName = approvalStatusName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getProjectTypeID() {
		return projectTypeID;
	}

	public void setProjectTypeID(String projectTypeID) {
		this.projectTypeID = projectTypeID;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRole() {

		if (role != null) {
			setRole(VisitService.ROLE_VISIT_CREATOR.equals(Byte.parseByte(role)) ? "发起人" : "支持人员");
		}
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleApprovalStatus() {
		return roleApprovalStatus;
	}

	public void setRoleApprovalStatus(String roleApprovalStatus) {
		this.roleApprovalStatus = roleApprovalStatus;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List getVisitContent() {
		return visitContent;
	}

	public void setVisitContent(List visitContent) {
		this.visitContent = visitContent;
	}

	public String getVisitDetail() {
		return visitDetail;
	}

	public void setVisitDetail(String visitDetail) {
		this.visitDetail = visitDetail;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getVisitTimeStr() {
		return visitTimeStr;
	}

	public void setVisitTimeStr(String visitTimeStr) {
		this.visitTimeStr = visitTimeStr;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

}
