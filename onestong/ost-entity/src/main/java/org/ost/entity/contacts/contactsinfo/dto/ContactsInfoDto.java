package org.ost.entity.contacts.contactsinfo.dto;

public class ContactsInfoDto {
	private Integer contactsInfoId;
	private String type;
	private String custName;
	private String val;

	public ContactsInfoDto() {
		super();
	}

	public ContactsInfoDto(String type, String custName, String val) {
		super();
		this.type = type;
		this.custName = custName;
		this.val = val;
	}

	public Integer getContactsInfoId() {
		return contactsInfoId;
	}

	public void setContactsInfoId(Integer contactsInfoId) {
		this.contactsInfoId = contactsInfoId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
