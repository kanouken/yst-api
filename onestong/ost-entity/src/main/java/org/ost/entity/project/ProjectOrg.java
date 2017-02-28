package org.ost.entity.project;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_organize_project")
public class ProjectOrg extends BaseEntity {
	private Integer organizeID;
	private Integer projectID;

	public Integer getOrganizeID() {
		return organizeID;
	}

	public void setOrganizeID(Integer organizeID) {
		this.organizeID = organizeID;
	}

	public Integer getProjectID() {
		return projectID;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

}
