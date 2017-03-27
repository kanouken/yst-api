package org.ost.crm.model.visit;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_visit_event_approval_user")
public class VisitApprover extends BaseEntity {
	private Integer visitEventID;
	private Integer userID;
	private String userName;
	private Integer organizeID;
	private String organizeName;
	private Byte role;
	private Byte approvalStatus;

	public Integer getVisitEventID() {
		return visitEventID;
	}

	public void setVisitEventID(Integer visitEventID) {
		this.visitEventID = visitEventID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getOrganizeID() {
		return organizeID;
	}

	public void setOrganizeID(Integer organizeID) {
		this.organizeID = organizeID;
	}

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}

	public Byte getRole() {
		return role;
	}

	public void setRole(Byte role) {
		this.role = role;
	}

	public Byte getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Byte approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

}
