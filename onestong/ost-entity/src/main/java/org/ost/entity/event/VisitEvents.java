package org.ost.entity.event;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "visit_events")
public class VisitEvents {

	public VisitEvents() {
		super();
	}

	public VisitEvents(String veId, Integer userId, Integer customerId, String content, Date visitedTime,
			String vistedLongitude, String visitedLatitude, Byte state, String creator, String updator, Byte valid,
			Date createTime, Date updateTime, String optional1) {
		super();
		this.veId = veId;
		this.userId = userId;
		this.customerId = customerId;
		this.content = content;
		this.visitedTime = visitedTime;
		this.vistedLongitude = vistedLongitude;
		this.visitedLatitude = visitedLatitude;
		this.state = state;
		this.creator = creator;
		this.updator = updator;
		this.valid = valid;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.optional1 = optional1;
	}

	@Id
	private String veId;

	private Date contactTime;
	private String contactType;
	private String projectName;
	private Integer projectId;
	private String busChange;
	private String customerName;
	private Integer userId;
	private Integer customerId;
	private String content;
	private Date visitedTime;
	private String vistedLongitude;
	private String visitedLatitude;
	private Byte state;
	private String creator;
	private String updator;
	private Byte valid;
	private Date createTime;
	private Date updateTime;
	private String optional1;

	public Date getContactTime() {
		return contactTime;
	}

	public void setContactTime(Date contactTime) {
		this.contactTime = contactTime;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getBusChange() {
		return busChange;
	}

	public void setBusChange(String busChange) {
		this.busChange = busChange;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getVeId() {
		return veId;
	}

	public void setVeId(String veId) {
		this.veId = veId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getVisitedTime() {
		return visitedTime;
	}

	public void setVisitedTime(Date visitedTime) {
		this.visitedTime = visitedTime;
	}

	public String getVistedLongitude() {
		return vistedLongitude;
	}

	public void setVistedLongitude(String vistedLongitude) {
		this.vistedLongitude = vistedLongitude;
	}

	public String getVisitedLatitude() {
		return visitedLatitude;
	}

	public void setVisitedLatitude(String visitedLatitude) {
		this.visitedLatitude = visitedLatitude;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Byte getValid() {
		return valid;
	}

	public void setValid(Byte valid) {
		this.valid = valid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOptional1() {
		return optional1;
	}

	public void setOptional1(String optional1) {
		this.optional1 = optional1;
	}

}
