package org.ost.crm.model.visit;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_visit_event_user")
public class VisitSupporter extends BaseEntity {

	private Integer visitEventID;
	private Integer userID;
	private String userName;
	private Integer organizeID;
	private String organizeName;
	private Byte role;
	private Object visitContent;
	private String visitDetail;
	private String attenceEventID;

	public VisitSupporter() {
		super();
	}

	public VisitSupporter(Integer visitEventID, Integer userID, String userName, Integer organizeID,
			String organizeName, Byte role) {
		super();
		this.visitEventID = visitEventID;
		this.userID = userID;
		this.userName = userName;
		this.organizeID = organizeID;
		this.organizeName = organizeName;
		this.role = role;
	}

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

	public Object getVisitContent() {
		return visitContent;
	}

	public void setVisitContent(Object visitContent) {
		this.visitContent = visitContent;
	}

	public String getVisitDetail() {
		return visitDetail;
	}

	public void setVisitDetail(String visitDetail) {
		this.visitDetail = visitDetail;
	}

	public String getAttenceEventID() {
		return attenceEventID;
	}

	public void setAttenceEventID(String attenceEventID) {
		this.attenceEventID = attenceEventID;
	}

}
