package org.ost.entity.customer;

import java.util.ArrayList;

import javax.persistence.Entity;

import org.ost.entity.base.BaseEntity;
import org.ost.entity.customer.vo.CustomerVo;
import org.ost.entity.location.vo.LocationVo;
import org.ost.entity.org.department.Departments;
import org.ost.entity.phone.PhoneVo;
import org.ost.entity.project.vo.ProjectVo;
import org.ost.entity.user.dto.UserListDto;

@Entity
public class Customer extends BaseEntity {
	     //客户行业 "belongIndustry": "零售连锁",
		 private String belongIndustry;
		 //联系人
		 private ArrayList<UserListDto> contactsVo;
		 //交易频次 "dealFrequency": "高频次（4次/月）",
		 private String dealFrequency;
	     //部门
		 private Departments deptOwnerVo;
		 //"isPlc": "非上市",
		 private String isPlc;
		 private ArrayList<LocationVo> locationsVo;
		 //客户经理
		 private CustomerVo managerOwnerVo;
	     //客户名称 "name": "测试",
	     private String name;
	     //公司性质 "nature": "国企",
	     private String nature;   
	     //上级客户
	     private CustomerVo parentVo;
	     //客户电话
	     private ArrayList<PhoneVo> phoneVo;
	     //项目
	     private ArrayList<ProjectVo> projectsVo; 
	     //客户来源 "source": "自己开发",
	     private String source;
	     //成交金额 "turnover": "1000万",
	     private String turnover;
	     //客户分类 "type": "最终用户"
	     private String type;
		public String getBelongIndustry() {
			return belongIndustry;
		}
		public void setBelongIndustry(String belongIndustry) {
			this.belongIndustry = belongIndustry;
		}
		public ArrayList<UserListDto> getContactsVo() {
			return contactsVo;
		}
		public void setContactsVo(ArrayList<UserListDto> contactsVo) {
			this.contactsVo = contactsVo;
		}
		public String getDealFrequency() {
			return dealFrequency;
		}
		public void setDealFrequency(String dealFrequency) {
			this.dealFrequency = dealFrequency;
		}
		public Departments getDeptOwnerVo() {
			return deptOwnerVo;
		}
		public void setDeptOwnerVo(Departments deptOwnerVo) {
			this.deptOwnerVo = deptOwnerVo;
		}
		public String getIsPlc() {
			return isPlc;
		}
		public void setIsPlc(String isPlc) {
			this.isPlc = isPlc;
		}
		public ArrayList<LocationVo> getLocationsVo() {
			return locationsVo;
		}
		public void setLocationsVo(ArrayList<LocationVo> locationsVo) {
			this.locationsVo = locationsVo;
		}
		public CustomerVo getManagerOwnerVo() {
			return managerOwnerVo;
		}
		public void setManagerOwnerVo(CustomerVo managerOwnerVo) {
			this.managerOwnerVo = managerOwnerVo;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNature() {
			return nature;
		}
		public void setNature(String nature) {
			this.nature = nature;
		}
		public CustomerVo getParentVo() {
			return parentVo;
		}
		public void setParentVo(CustomerVo parentVo) {
			this.parentVo = parentVo;
		}
		public ArrayList<PhoneVo> getPhoneVo() {
			return phoneVo;
		}
		public void setPhoneVo(ArrayList<PhoneVo> phoneVo) {
			this.phoneVo = phoneVo;
		}
		public ArrayList<ProjectVo> getProjectsVo() {
			return projectsVo;
		}
		public void setProjectsVo(ArrayList<ProjectVo> projectsVo) {
			this.projectsVo = projectsVo;
		}
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getTurnover() {
			return turnover;
		}
		public void setTurnover(String turnover) {
			this.turnover = turnover;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}

}
