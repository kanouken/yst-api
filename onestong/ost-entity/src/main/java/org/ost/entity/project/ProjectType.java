package org.ost.entity.project;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_project")
public class ProjectType extends BaseEntity {
	private Integer id;

	private String name;

	private Integer cycWarningDay;

	private String cycWarningEnable;

	private Integer startTimeWarningDay;

	private String startTimeWarningEnable;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

}