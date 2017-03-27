package org.ost.crm.model.visit;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_visit_event_project")
public class VisitProject extends BaseEntity {
	private Integer visitEventID;
	private Integer projectID;

	public Integer getVisitEventID() {
		return visitEventID;
	}

	public void setVisitEventID(Integer visitEventID) {
		this.visitEventID = visitEventID;
	}

	public Integer getProjectID() {
		return projectID;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

}
