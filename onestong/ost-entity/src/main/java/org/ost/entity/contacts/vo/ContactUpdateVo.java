package org.ost.entity.contacts.vo;


import java.util.List;

import org.ost.entity.contacts.Address;
import org.ost.entity.contacts.ContactInfo;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.location.vo.LocationVo;
import org.ost.entity.phone.PhoneVo;


public class ContactUpdateVo{
	
	private Integer id;
	//客户
	private CustomerVo customerVo;			
	//部门	
	private String dept;
	//
	private List<String> email;		
	//姓名     		
	private String name;
	//职位
	private String position;
	//地址
	private List<Address> locationVo;
	//
    private List<ContactInfo> phoneVo;
    //性别	
    private String sex	;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public List<String> getEmail() {
		return email;
	}
	public void setEmail(List<String> email) {
		this.email = email;
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
	public List<Address> getLocationVo() {
		return locationVo;
	}
	public void setLocationVo(List<Address> locationVo) {
		this.locationVo = locationVo;
	}
	public List<ContactInfo> getPhoneVo() {
		return phoneVo;
	}
	public void setPhoneVo(List<ContactInfo> phoneVo) {
		this.phoneVo = phoneVo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
}
