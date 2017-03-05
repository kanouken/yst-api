package org.ost.entity.customer.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_user_customer")
public class UserCustomers extends BaseEntity {
	private Integer userId;
	private Integer customerId;
	private String userName;
	private Integer organizeId;
	private String organizeName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(Integer organizeId) {
		this.organizeId = organizeId;
	}

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}

}
