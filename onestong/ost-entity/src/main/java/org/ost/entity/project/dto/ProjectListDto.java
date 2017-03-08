package org.ost.entity.project.dto;

import java.util.Date;
import java.util.List;

import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.org.department.dto.DepartmentListDto;
import org.ost.entity.user.dto.UserListDto;

public class ProjectListDto {
	private String amount;

	private CurrentStep currentStep;

	private CustomerVo customer;

	private String cyc;

	private List<DepartmentListDto> deptOwner;

	private String details;

	private String id;
	private String isCyc;

	private List<UserListDto> managerOwner;

	private String name;
	private Date startTime;
	private String startTimeStr;
	private String state;

	private String stateName;
	private String typeID;
	private String typeName;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public CurrentStep getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(CurrentStep currentStep) {
		this.currentStep = currentStep;
	}

	public CustomerVo getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerVo customer) {
		this.customer = customer;
	}

	public String getCyc() {
		return cyc;
	}

	public void setCyc(String cyc) {
		this.cyc = cyc;
	}

	public List<DepartmentListDto> getDeptOwner() {
		return deptOwner;
	}

	public void setDeptOwner(List<DepartmentListDto> deptOwner) {
		this.deptOwner = deptOwner;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsCyc() {
		return isCyc;
	}

	public void setIsCyc(String isCyc) {
		this.isCyc = isCyc;
	}

	public List<UserListDto> getManagerOwner() {
		return managerOwner;
	}

	public void setManagerOwner(List<UserListDto> managerOwner) {
		this.managerOwner = managerOwner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public static class CurrentStep {
		private String id;

		private String isLastStep;

		private String isOverTime;

		private String memo;
		private String stepDay;

		private Date time;
		private String timeStr;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getIsLastStep() {
			return isLastStep;
		}

		public void setIsLastStep(String isLastStep) {
			this.isLastStep = isLastStep;
		}

		public String getIsOverTime() {
			return isOverTime;
		}

		public void setIsOverTime(String isOverTime) {
			this.isOverTime = isOverTime;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public String getStepDay() {
			return stepDay;
		}

		public void setStepDay(String stepDay) {
			this.stepDay = stepDay;
		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}

		public String getTimeStr() {
			return timeStr;
		}

		public void setTimeStr(String timeStr) {
			this.timeStr = timeStr;
		}

	}
}
