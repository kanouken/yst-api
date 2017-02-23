package org.ost.entity.contacts;


import javax.persistence.Entity;

import org.ost.entity.base.BaseEntity;


@Entity
public class Address extends BaseEntity {
	private Integer id;
	private Integer contactId;
	private String province;
	private String city;
	private String district;	
	private String detailAddress1;
	private String detailAddress2;
	private String detailAddress3;
	private String lat;
	private String lng;
	private String schemaId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getDetailAddress1() {
		return detailAddress1;
	}
	public void setDetailAddress1(String detailAddress1) {
		this.detailAddress1 = detailAddress1;
	}
	public String getDetailAddress2() {
		return detailAddress2;
	}
	public void setDetailAddress2(String detailAddress2) {
		this.detailAddress2 = detailAddress2;
	}
	public String getDetailAddress3() {
		return detailAddress3;
	}
	public void setDetailAddress3(String detailAddress3) {
		this.detailAddress3 = detailAddress3;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getSchemaId() {
		return schemaId;
	}
	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}
	
}
