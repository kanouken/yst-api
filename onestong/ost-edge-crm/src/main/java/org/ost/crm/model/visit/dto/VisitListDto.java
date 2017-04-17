package org.ost.crm.model.visit.dto;

public class VisitListDto {
	private String id;

	private String visitTimeStr;

	private String visitType;

	private String createBy;

	private String contact;

	private String busChange;

	private String customerId;
	private String customerName;

	private String visitDetail;

	public String getVisitDetail() {
		return visitDetail;
	}

	public void setVisitDetail(String visitDetail) {
		this.visitDetail = visitDetail;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVisitTimeStr() {
		return visitTimeStr;
	}

	public void setVisitTimeStr(String visitTimeStr) {
		this.visitTimeStr = visitTimeStr;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getBusChange() {
		return busChange;
	}

	public void setBusChange(String busChange) {
		this.busChange = busChange;
	}

}
