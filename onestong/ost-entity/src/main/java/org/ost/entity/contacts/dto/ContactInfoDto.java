package org.ost.entity.contacts.dto;

import java.util.List;

import org.ost.entity.contacts.Address;
import org.ost.entity.contacts.ContactInfo;
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
		private List<ContactInfo> contactInfo;	
		//电话
		private List<Address> address;
		
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
		public List<ContactInfo> getContactInfo() {
			return contactInfo;
		}
		public void setContactInfo(List<ContactInfo> contactInfo) {
			this.contactInfo = contactInfo;
		}
		public List<Address> getAddress() {
			return address;
		}
		public void setAddress(List<Address> address) {
			this.address = address;
		}
		

	
}
