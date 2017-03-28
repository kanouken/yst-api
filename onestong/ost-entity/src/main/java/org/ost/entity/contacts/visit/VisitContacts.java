package org.ost.entity.contacts.visit;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_visit_event_contact")
public class VisitContacts extends BaseEntity {
	private Integer visitEventID;
	private Integer contactID;

	public Integer getVisitEventID() {
		return visitEventID;
	}

	public void setVisitEventID(Integer visitEventID) {
		this.visitEventID = visitEventID;
	}

	public Integer getContactID() {
		return contactID;
	}

	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}

}
