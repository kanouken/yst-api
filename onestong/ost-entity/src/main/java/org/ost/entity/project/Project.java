package org.ost.entity.project;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_project")
public class Project extends BaseEntity {
	private String name;
	private Integer projectTypeID;
	private Byte state;
	private Date startTime;

	private String detail;

	private Integer isCyc;

	private Integer cyc;
	private Integer amount;

	@Transient
	private List<ProjectTypeStep> typeSteps;
	@Transient
	private List<ProjectStep> historySteps;

	public List<ProjectStep> getHistorySteps() {
		return historySteps;
	}

	public void setHistorySteps(List<ProjectStep> historySteps) {
		this.historySteps = historySteps;
	}

	public List<ProjectTypeStep> getTypeSteps() {
		return typeSteps;
	}

	public void setTypeSteps(List<ProjectTypeStep> typeSteps) {
		this.typeSteps = typeSteps;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProjectTypeID() {
		return projectTypeID;
	}

	public void setProjectTypeID(Integer projectTypeID) {
		this.projectTypeID = projectTypeID;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getIsCyc() {
		return isCyc;
	}

	public void setIsCyc(Integer isCyc) {
		this.isCyc = isCyc;
	}

	public Integer getCyc() {
		return cyc;
	}

	public void setCyc(Integer cyc) {
		this.cyc = cyc;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
