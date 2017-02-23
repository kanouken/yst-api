package org.ost.entity.customer.contacts;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_contact_info")
public class ContactInfo extends BaseEntity {
	private Integer customerId;
	private String type;
	private String custName;
	private String val;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
