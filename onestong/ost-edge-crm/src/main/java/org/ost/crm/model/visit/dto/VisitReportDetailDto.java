package org.ost.crm.model.visit.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author xnq
 *
 */
public class VisitReportDetailDto {

	private Integer visitId;
	private String customerName;

	private String visitType;

	private Date visitTime;

	private String visitTimeStr;

	private Date createTime;

	private String createTimeStr;

	private List<VisitSupportDto> supporters;

	public Date getVisitTime() {
		return visitTime;
	}

	public String getVisitTimeStr() {
		return visitTimeStr;
	}

	public void setVisitTimeStr(String visitTimeStr) {
		this.visitTimeStr = visitTimeStr;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Integer getVisitId() {
		return visitId;
	}

	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<VisitSupportDto> getSupporters() {
		return supporters;
	}

	public void setSupporters(List<VisitSupportDto> supporters) {
		this.supporters = supporters;
	}

}
