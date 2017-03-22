package com.oz.onestong.model.report;

import java.io.Serializable;

public class AttenceReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5802988910369706636L;
	/**
	 * 用户
	 */
	private String  creator;
	/**
	 * 签到地址
	 */
	private String[] address;
	/**
	 * 签到时间
	 */
	private String[] times;
	
	/**
	 * 日期
	 */
	private String[] days;
	
	/**
	 * 迟到 或者 早退 数据  韦德暂时用不到
	 */
	private String[] notOnTimes;

	/**   FIXME 客户端 不可以输入 _ 
	 *签到 或者签退 的批注
	 */
	private String attenceNotice;
	/**
	 *  签到 or 签退 的地点偏移
	 */
	private String attenceOffset;
	
	
	public String getAttenceOffset() {
		return attenceOffset;
	}

	public void setAttenceOffset(String attenceOffset) {
		this.attenceOffset = attenceOffset;
	}

	public String getAttenceNotice() {
		return attenceNotice;
	}

	public void setAttenceNotice(String attenceNotice) {
		this.attenceNotice = attenceNotice;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String[] getAddress() {
		return address;
	}

	public void setAddress(String[] address) {
		this.address = address;
	}

	public String[] getTimes() {
		return times;
	}

	public void setTimes(String[] times) {
		this.times = times;
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String[] days) {
		this.days = days;
	}

	public String[] getNotOnTimes() {
		return notOnTimes;
	}

	public void setNotOnTimes(String[] notOnTimes) {
		this.notOnTimes = notOnTimes;
	}
	
	
	
}
