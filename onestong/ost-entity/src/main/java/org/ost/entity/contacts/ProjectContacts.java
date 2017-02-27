package org.ost.entity.contacts;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name="tbl_project_contact")
public class ProjectContacts extends BaseEntity {
	private Integer projectID;
	private Integer contactID;

	public Integer getProjectID() {
		return projectID;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	public Integer getContactID() {
		return contactID;
	}

	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}

}
