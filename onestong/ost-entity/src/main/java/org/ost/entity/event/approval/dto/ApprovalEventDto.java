package org.ost.entity.event.approval.dto;

import java.util.List;

import org.ost.entity.user.dto.UserListDto;

public class ApprovalEventDto {

	private String userPic;

	private String creator;

	private String createTime;

	private String approvalTip;

	private String approvalEventId;

	private String applyEndTime;

	private String applyStartTime;

	private String applyType;

	private List<UserListDto> appprovers;

	private String busTripReason;

	private String leaveReason;
	private String leaveType;

	private String scheduling;

	private String status;

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getApprovalTip() {
		return approvalTip;
	}

	public void setApprovalTip(String approvalTip) {
		this.approvalTip = approvalTip;
	}

	public String getApprovalEventId() {
		return approvalEventId;
	}

	public void setApprovalEventId(String approvalEventId) {
		this.approvalEventId = approvalEventId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(String applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public String getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyStartTime(String applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public List<UserListDto> getAppprovers() {
		return appprovers;
	}

	public void setAppprovers(List<UserListDto> appprovers) {
		this.appprovers = appprovers;
	}

	public String getBusTripReason() {
		return busTripReason;
	}

	public void setBusTripReason(String busTripReason) {
		this.busTripReason = busTripReason;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getScheduling() {
		return scheduling;
	}

	public void setScheduling(String scheduling) {
		this.scheduling = scheduling;
	}

}
