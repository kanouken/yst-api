package org.ost.entity.event.approval.dto;

public class ApprovalListDto {
	private String createTime;

	private String status;
	private String approvalType;

	private String approvalTip;

	private String approvalId;

	private Integer userId;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getApprovalTip() {
		return approvalTip;
	}

	public void setApprovalTip(String approvalTip) {
		this.approvalTip = approvalTip;
	}

	public String getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
