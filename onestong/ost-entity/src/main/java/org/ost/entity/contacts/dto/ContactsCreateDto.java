package org.ost.entity.contacts.dto;

import java.util.List;

import org.ost.entity.contacts.address.dto.ContactsAddressDto;
import org.ost.entity.contacts.contactsinfo.dto.ContactsInfoDto;
import org.ost.entity.contacts.file.dto.ContactsFileDto;
import org.ost.entity.customer.dto.CustomerListDto;
import org.ost.entity.customer.vo.CustomerVo;

public class ContactsCreateDto {

	private CustomerVo customer;

	private String dept;
	// 邮箱
	private List<String> email;
	private String headPic;
	private List<ContactsAddressDto> locations;
	private String name;
	private List<ContactsInfoDto> phone;
	private List<ContactsFileDto> photo;
	private String position;
	private String sex;
	private String schemaId;
	private String currentUserName;
	private String py;
	private String sexName;
	private String szm;

	public CustomerVo getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerVo customer) {
		this.customer = customer;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getSzm() {
		return szm;
	}

	public void setSzm(String szm) {
		this.szm = szm;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public List<String> getEmail() {
		return email;
	}

	public void setEmail(List<String> email) {
		this.email = email;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public List<ContactsAddressDto> getLocations() {
		return locations;
	}

	public void setLocations(List<ContactsAddressDto> locations) {
		this.locations = locations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ContactsInfoDto> getPhone() {
		return phone;
	}

	public void setPhone(List<ContactsInfoDto> phone) {
		this.phone = phone;
	}

	public List<ContactsFileDto> getPhoto() {
		return photo;
	}

	public void setPhoto(List<ContactsFileDto> photo) {
		this.photo = photo;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}
}
