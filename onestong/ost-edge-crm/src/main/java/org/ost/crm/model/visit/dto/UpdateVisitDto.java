package org.ost.crm.model.visit.dto;

import java.util.List;

import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.project.dto.ProjectListDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UpdateVisitDto {

	/**
	 * 联系人
	 */
	@ApiModelProperty(value = "联系人")
	private List<ContactsListDto> contacts;

	/**
	 * 商机 》》 项目类别
	 */
	@ApiModelProperty(value = "商机")
	private String projectTypeID;
	/**
	 * 关联项目
	 */
	@ApiModelProperty(value = "关联项目")
	private List<ProjectListDto> projects;

	private List<String> visitContent;
	private String visitDetail;

	public List<ContactsListDto> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactsListDto> contacts) {
		this.contacts = contacts;
	}

	public String getProjectTypeID() {
		return projectTypeID;
	}

	public void setProjectTypeID(String projectTypeID) {
		this.projectTypeID = projectTypeID;
	}

	public List<ProjectListDto> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectListDto> projects) {
		this.projects = projects;
	}

	public List<String> getVisitContent() {
		return visitContent;
	}

	public void setVisitContent(List<String> visitContent) {
		this.visitContent = visitContent;
	}

	public String getVisitDetail() {
		return visitDetail;
	}

	public void setVisitDetail(String visitDetail) {
		this.visitDetail = visitDetail;
	}

}