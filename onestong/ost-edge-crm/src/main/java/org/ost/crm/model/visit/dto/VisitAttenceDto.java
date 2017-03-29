package org.ost.crm.model.visit.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class VisitAttenceDto {
	@ApiModelProperty(value = "")
	private String signInAddress;
	@ApiModelProperty(value = "签到时间差（分钟） 负数表示比标准时间早 正数表示比标准时间晚")
	private String signInDiffTime;
	@ApiModelProperty(value = "签到地址-纬度")
	private String signInLat;
	@ApiModelProperty(value = "签到地址-经度")
	private String signInLng;
	@ApiModelProperty(value = "签到状态 0:未签到 1:已签到")
	private String signInState;
	@ApiModelProperty(value = "签到时间")
	private Date signInTime;
	@ApiModelProperty(value = "2017-02-12 13:45")
	private String signInTimeStr;
	@ApiModelProperty(value = "签到是否合规 0:不合规 1:合规")
	private String signInValid;
	@ApiModelProperty(value = "签退地址")
	private String signOutAddress;
	@ApiModelProperty(value = "签退时间差（分钟） 负数表示比标准时间早 正数表示比标准时间晚")
	private String signOutDiffTime;
	@ApiModelProperty(value = "签退地址-纬度")
	private String signOutLat;
	@ApiModelProperty(value = "签退地址-经度")
	private String signOutLng;
	@ApiModelProperty(value = "签退状态 0:未签到 1:已签到")
	private String signOutState;
	private Date signOutTime;
	private String signOutTimeStr;
	@ApiModelProperty(value = "签退是否合规 0:不合规 1:合规")
	private String signOutValid;
	@ApiModelProperty(value = "考勤状态 1:没有签退 2:签退 但是 没有签到 3:既签到又签退")
	private String state;
	private String userID;

	public String getSignInAddress() {
		return signInAddress;
	}

	public void setSignInAddress(String signInAddress) {
		this.signInAddress = signInAddress;
	}

	public String getSignInDiffTime() {
		return signInDiffTime;
	}

	public void setSignInDiffTime(String signInDiffTime) {
		this.signInDiffTime = signInDiffTime;
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

	public String getSignInState() {
		return signInState;
	}

	public void setSignInState(String signInState) {
		this.signInState = signInState;
	}

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public String getSignInTimeStr() {
		return signInTimeStr;
	}

	public void setSignInTimeStr(String signInTimeStr) {
		this.signInTimeStr = signInTimeStr;
	}

	public String getSignInValid() {
		return signInValid;
	}

	public void setSignInValid(String signInValid) {
		this.signInValid = signInValid;
	}

	public String getSignOutAddress() {
		return signOutAddress;
	}

	public void setSignOutAddress(String signOutAddress) {
		this.signOutAddress = signOutAddress;
	}

	public String getSignOutDiffTime() {
		return signOutDiffTime;
	}

	public void setSignOutDiffTime(String signOutDiffTime) {
		this.signOutDiffTime = signOutDiffTime;
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

	public String getSignOutState() {
		return signOutState;
	}

	public void setSignOutState(String signOutState) {
		this.signOutState = signOutState;
	}

	public Date getSignOutTime() {
		return signOutTime;
	}

	public void setSignOutTime(Date signOutTime) {
		this.signOutTime = signOutTime;
	}

	public String getSignOutTimeStr() {
		return signOutTimeStr;
	}

	public void setSignOutTimeStr(String signOutTimeStr) {
		this.signOutTimeStr = signOutTimeStr;
	}

	public String getSignOutValid() {
		return signOutValid;
	}

	public void setSignOutValid(String signOutValid) {
		this.signOutValid = signOutValid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
