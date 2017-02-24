package org.ost.entity.contacts.file;

import javax.persistence.Entity;

import org.ost.entity.base.BaseEntity;

@Entity
public class ContactsFile extends BaseEntity {
	private Integer contactID;
	private String type;
	private String val;

	public Integer getContactID() {
		return contactID;
	}

	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
