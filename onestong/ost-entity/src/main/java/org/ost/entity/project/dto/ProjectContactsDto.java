package org.ost.entity.project.dto;

import java.util.List;

import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.project.vo.ProjectVo;

public class ProjectContactsDto {
	private Integer id;
	private String name;

	private Integer userID;
	private String userName;

	private Integer projectID;

	private Integer deptID;
	private String deptName;

	private ProjectVo project;

	private List<ContactsListDto> contacts;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ProjectVo getProject() {
		return project;
	}

	public void setProject(ProjectVo project) {
		this.project = project;
	}

	public List<ContactsListDto> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactsListDto> contacts) {
		this.contacts = contacts;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getProjectID() {
		return projectID;
	}

	public void setProjectID(Integer projectID) {
		this.projectID = projectID;
	}

	public Integer getDeptID() {
		return deptID;
	}

	public void setDeptID(Integer deptID) {
		this.deptID = deptID;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

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
}
