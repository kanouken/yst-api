package org.ost.crm.model.visit.dto;

import io.swagger.annotations.ApiModelProperty;

public class VisitAttenceCreateDto {

	@ApiModelProperty("内容")
	private String content;
	@ApiModelProperty("签到地址：省市区详细地址")
	private String signInAddress;
	@ApiModelProperty("纬度")
	private String signInLat;
	@ApiModelProperty("经度")
	private String signInLng;

	@ApiModelProperty("签退地址：省市区详细地址")
	private String signOutAddress;
	@ApiModelProperty("签退 纬度")
	private String signOutLat;
	@ApiModelProperty("签退 经度")
	private String signOutLng;

	public String getSignOutAddress() {
		return signOutAddress;
	}

	public void setSignOutAddress(String signOutAddress) {
		this.signOutAddress = signOutAddress;
	}

	public String getSignOutLat() {
		return signOutLat;
	}

	public void setSignOutLat(String signOutLat) {
		this.signOutLat = signOutLat;
	}

	public String getSignOutLng() {
		return signOutLng;
	}

	public void setSignOutLng(String signOutLng) {
		this.signOutLng = signOutLng;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSignInAddress() {
		return signInAddress;
	}

	public void setSignInAddress(String signInAddress) {
		this.signInAddress = signInAddress;
	}

	public String getSignInLat() {
		return signInLat;
	}

	public void setSignInLat(String signInLat) {
		this.signInLat = signInLat;
	}

	public String getSignInLng() {
		return signInLng;
	}

	public void setSignInLng(String signInLng) {
		this.signInLng = signInLng;
	}

}
