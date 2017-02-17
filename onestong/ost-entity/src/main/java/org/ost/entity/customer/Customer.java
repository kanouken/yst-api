package org.ost.entity.customer;

import javax.persistence.Entity;

import org.ost.entity.base.BaseEntity;

@Entity
public class Customer extends BaseEntity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
