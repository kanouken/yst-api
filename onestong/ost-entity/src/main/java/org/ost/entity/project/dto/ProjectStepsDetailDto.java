package org.ost.entity.project.dto;

import java.util.List;
import java.util.Map;

public class ProjectStepsDetailDto {

	private Map<String, Object> cycWarning;

	private Map<String, Object> startTimeWarning;

	private List<ProjectStepsDto> steps;

	private Integer typeID;

	private String typeName;

	public Map<String, Object> getCycWarning() {
		return cycWarning;
	}

	public void setCycWarning(Map<String, Object> cycWarning) {
		this.cycWarning = cycWarning;
	}

	public Map<String, Object> getStartTimeWarning() {
		return startTimeWarning;
	}

	public void setStartTimeWarning(Map<String, Object> startTimeWarning) {
		this.startTimeWarning = startTimeWarning;
	}

	public List<ProjectStepsDto> getSteps() {
		return steps;
	}

	public void setSteps(List<ProjectStepsDto> steps) {
		this.steps = steps;
	}

	public Integer getTypeID() {
		return typeID;
	}

	public void setTypeID(Integer typeID) {
		this.typeID = typeID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
