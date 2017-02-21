package org.ost.entity.contacts.vo;


import java.util.List;

import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.location.vo.LocationVo;
import org.ost.entity.phone.PhoneVo;


public class ContactUpdateVo{
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
	private LocationVo locationVo;
	//
    private PhoneVo phoneVo;
    //性别	
    private String sex	;
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
	public LocationVo getLocationVo() {
		return locationVo;
	}
	public void setLocationVo(LocationVo locationVo) {
		this.locationVo = locationVo;
	}
	public PhoneVo getPhoneVo() {
		return phoneVo;
	}
	public void setPhoneVo(PhoneVo phoneVo) {
		this.phoneVo = phoneVo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
}
