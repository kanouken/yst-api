package org.ost.entity.project.dto;

import java.util.Map;
/**
 * 项目类型阶段
 * @author xnq
 *
 */
public class ProjectStepsDto {
	private Map<String, Object> warning;

	private Integer id;
	private String memo;
	private String rate;
	private String step;

	private String timeStr;

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public Map<String, Object> getWarning() {
		return warning;
	}

	public void setWarning(Map<String, Object> warning) {
		this.warning = warning;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

}
