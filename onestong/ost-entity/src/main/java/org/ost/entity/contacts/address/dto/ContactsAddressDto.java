package org.ost.entity.contacts.address.dto;

public class ContactsAddressDto {
	private String province;
	private String city;
	private String district;
	private String detailAddress1;
	private String detailAddress2;
	private String detailAddress3;
	private Double lat;
	private Double lng;

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

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

}
