package org.ost.entity.contacts;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_customer_contact")
public class CustomerContacts extends BaseEntity {

	private Integer customerID;
	private Integer contactID;

	public Integer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}

	public Integer getContactID() {
		return contactID;
	}

	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}

}
