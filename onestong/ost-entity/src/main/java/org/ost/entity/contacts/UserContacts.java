package org.ost.entity.contacts;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_user_contact")
public class UserContacts extends BaseEntity {

	private Integer userID;
	private Integer contactID;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getContactID() {
		return contactID;
	}

	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}

}
