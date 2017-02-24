package org.ost.entity.contacts.dto;

import java.util.List;

import org.ost.entity.contacts.address.ContactsAddress;
import org.ost.entity.contacts.contactsinfo.ContactsInfo;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.location.vo.LocationVo;
import org.ost.entity.phone.PhoneVo;

public class ContactInfoDto {
	    //客户
		private CustomerVo customerVo;
		//部门	
		private String dept;
		private Integer  id;		
		//联系人姓名     		
		private String name;
		//职位
		private String position;
		//姓名拼音
		private String py;		
		//性别名称	
		private String sexName;	
		//拼音首字母
		private String szm;
		//电话
		private List<ContactsInfo> contactInfo;	
		//电话
		private List<ContactsAddress> address;
		
		public CustomerVo getCustomerVo() {
			return customerVo;
		}
		public void setCustomerVo(CustomerVo customerVo) {
			this.customerVo = customerVo;
		}
		public String getDept() {
			return dept;
		}
		public void setDept(String dept) {
			this.dept = dept;
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
		public String getPosition() {
			return position;
		}
		public void setPosition(String position) {
			this.position = position;
		}
		public String getPy() {
			return py;
		}
		public void setPy(String py) {
			this.py = py;
		}
		public String getSexName() {
			return sexName;
		}
		public void setSexName(String sexName) {
			this.sexName = sexName;
		}
		public String getSzm() {
			return szm;
		}
		public void setSzm(String szm) {
			this.szm = szm;
		}
		public List<ContactsInfo> getContactInfo() {
			return contactInfo;
		}
		public void setContactInfo(List<ContactsInfo> contactInfo) {
			this.contactInfo = contactInfo;
		}
		public List<ContactsAddress> getAddress() {
			return address;
		}
		public void setAddress(List<ContactsAddress> address) {
			this.address = address;
		}
		

	
}
