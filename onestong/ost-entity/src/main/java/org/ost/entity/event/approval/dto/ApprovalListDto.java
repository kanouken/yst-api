package org.ost.entity.event.approval.dto;

public class ApprovalListDto {
	private String createTime;

	private String status;
	private String statusName;
	private String approvalType;

	private String approvalTip;

	private String eId;

	public String getStatusName() {
		if (this.status.equals("0")) {
			this.statusName = "未审批";
		} else if (this.status.equals("1")) {
			this.statusName = "审批通过";
		} else if (this.status.equals("2")) {
			this.statusName = "审批不通过";
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
	}

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

}
