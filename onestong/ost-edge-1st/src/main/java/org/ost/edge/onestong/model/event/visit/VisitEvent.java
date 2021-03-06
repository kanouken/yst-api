package org.ost.edge.onestong.model.event.visit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "visit_events")
public class VisitEvent {
	@Id
	private String veId;

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
	/**
	 * 客户名称
	 */
	private String optional1;

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
