package org.ost.entity.project;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_project_steps")
public class ProjectStep extends BaseEntity implements Comparable<ProjectStep> {
	private Integer projectID;
	private Integer projectTypeStepID;

	private Date time;

	public Integer getProjectID() {
		return projectID;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	public Integer getProjectTypeStepID() {
		return projectTypeStepID;
	}

	public void setProjectTypeStepID(Integer projectTypeStepID) {
		this.projectTypeStepID = projectTypeStepID;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public int compareTo(ProjectStep o) {
		return o.getId().compareTo(this.getId());
	}

}
