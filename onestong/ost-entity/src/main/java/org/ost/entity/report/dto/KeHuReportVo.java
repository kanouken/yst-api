package org.ost.entity.report.dto;

import java.util.Date;

public class KeHuReportVo {
	private Date createTimeStr;

	private Integer newCustomerCount;
	private Integer totalCustomerCount;
	public Date getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(Date createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Integer getNewCustomerCount() {
		return newCustomerCount;
	}
	public void setNewCustomerCount(Integer newCustomerCount) {
		this.newCustomerCount = newCustomerCount;
	}
	public Integer getTotalCustomerCount() {
		return totalCustomerCount;
	}
	public void setTotalCustomerCount(Integer totalCustomerCount) {
		this.totalCustomerCount = totalCustomerCount;
	}
	
}
