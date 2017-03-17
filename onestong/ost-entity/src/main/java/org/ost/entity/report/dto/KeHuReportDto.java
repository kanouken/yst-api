package org.ost.entity.report.dto;

import java.util.Date;

/**
 * 客户报表
 * 
 * @author chenyingwen
 *
 */
public class KeHuReportDto {
	private Integer id;
	private String name;
	private String type;
	private String dealFrequency;
	private String turnover;
	private String isPlc;
	private String nature;
	private String source;
	private String belongIndustry;
	private Date createTime;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDealFrequency() {
		return dealFrequency;
	}

	public void setDealFrequency(String dealFrequency) {
		this.dealFrequency = dealFrequency;
	}

	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getIsPlc() {
		return isPlc;
	}

	public void setIsPlc(String isPlc) {
		this.isPlc = isPlc;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBelongIndustry() {
		return belongIndustry;
	}

	public void setBelongIndustry(String belongIndustry) {
		this.belongIndustry = belongIndustry;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
