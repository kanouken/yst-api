package org.ost.entity.project.dto;

import java.util.List;

import org.ost.entity.contacts.dto.ContactsListDto;
import org.ost.entity.project.vo.ProjectVo;

public class ProjectContactsDto {
	private String userName;

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

}
