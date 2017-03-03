package org.ost.entity.project.vo;

public class ProjectTypeVo {

	private String name;

	private Integer cycWarningDay;

	private String cycWarningEnable;

	private Integer startTimeWarningDay;

	private String startTimeWarningEnable;

	private String createBy;

	private String updateBy;

	private String schemaId;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public Integer getCycWarningDay() {

		return cycWarningDay;

	}

	public void setCycWarningDay(Integer cycWarningDay) {

		this.cycWarningDay = cycWarningDay;

	}

	public String getCycWarningEnable() {

		return cycWarningEnable;

	}

	public void setCycWarningEnable(String cycWarningEnable) {

		this.cycWarningEnable = cycWarningEnable;

	}

	public Integer getStartTimeWarningDay() {

		return startTimeWarningDay;

	}

	public void setStartTimeWarningDay(Integer startTimeWarningDay) {

		this.startTimeWarningDay = startTimeWarningDay;

	}

	public String getStartTimeWarningEnable() {

		return startTimeWarningEnable;

	}

	public void setStartTimeWarningEnable(String startTimeWarningEnable) {

		this.startTimeWarningEnable = startTimeWarningEnable;

	}

	public String getCreateBy() {

		return createBy;

	}

	public void setCreateBy(String createBy) {

		this.createBy = createBy;

	}


	public String getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}

	public String getUpdateBy() {

		return updateBy;

	}

	public void setUpdateBy(String updateBy) {

		this.updateBy = updateBy;

	}

}