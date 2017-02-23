package org.ost.entity.contacts;


import javax.persistence.Entity;

import org.ost.entity.base.BaseEntity;


@Entity
public class Contact extends BaseEntity {
	private Integer id;
	private String lastName;
	private String firstName;
	private String py;
	private String szm;
	private String sex;
	private String position;
	private String dept;
	private String schemaId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	public String getSzm() {
		return szm;
	}
	public void setSzm(String szm) {
		this.szm = szm;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getSchemaId() {
		return schemaId;
	}
	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}
	
}
