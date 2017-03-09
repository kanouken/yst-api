package org.ost.entity.project.dto;

public class ProjectTypeStepDto {
	private String memo;
	private Double step;
	private Integer rate;
	
	private Integer day;
	private Byte isEnable;
	
	private Integer projectTypeID;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Double getStep() {
		return step;
	}

	public void setStep(Double step) {
		this.step = step;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Byte getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Byte isEnable) {
		this.isEnable = isEnable;
	}

	public Integer getProjectTypeID() {
		return projectTypeID;
	}

	public void setProjectTypeID(Integer projectTypeID) {
		this.projectTypeID = projectTypeID;
	}
} 
