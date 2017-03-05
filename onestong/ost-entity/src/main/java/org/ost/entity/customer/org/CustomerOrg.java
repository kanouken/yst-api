package org.ost.entity.customer.org;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_organize_customer")
public class CustomerOrg extends BaseEntity {
	private Integer organizeId;
	private Integer customerId;
	private String organizeName;

	
	
	public Integer getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(Integer organizeId) {
		this.organizeId = organizeId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}

}
