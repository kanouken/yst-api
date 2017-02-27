package org.ost.entity.contacts;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name="tbl_organize_contact")
public class OrgContacts extends BaseEntity {
	private Integer organizeID;
	private Integer contactID;

	public Integer getOrganizeID() {
		return organizeID;
	}

	public void setOrganizeID(Integer organizeID) {
		this.organizeID = organizeID;
	}

	public Integer getContactID() {
		return contactID;
	}

	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}

}
