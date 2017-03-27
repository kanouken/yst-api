package org.ost.entity.contacts.dto;

import java.util.List;

public class VisitContactsDto {
	private Integer visitEventId;
	private List<Integer> contactsIds;

	private String userName;

	private String userId;

	public Integer getVisitEventId() {
		return visitEventId;
	}

	public void setVisitEventId(Integer visitEventId) {
		this.visitEventId = visitEventId;
	}

	public List<Integer> getContactsIds() {
		return contactsIds;
	}

	public void setContactsIds(List<Integer> contactsIds) {
		this.contactsIds = contactsIds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
