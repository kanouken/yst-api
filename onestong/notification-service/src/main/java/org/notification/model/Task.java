package org.notification.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.util.StringUtils;

public class Task {
	private String key = "";

	public String getKey() {

		if (StringUtils.isEmpty(this.params) || StringUtils.isEmpty(this.hookMethod)) {
			this.key = null;
		} else {
			this.key += (this.getUuid() + ":" + this.hookMethod + ":"+this.params);
		}
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Task [params=" + params + ", uuid=" + uuid + ", hookMethod=" + hookMethod + ", delayTime=" + delayTime
				+ ", createTime=" + createTime + "]";
	}

	private String params;

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	private String uuid;

	public String getUuid() {
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {
			hashCodeV = -hashCodeV;
		}
		return String.format("%010d", hashCodeV);
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getHookMethod() {
		return hookMethod;
	}

	public void setHookMethod(String hookMethod) {
		this.hookMethod = hookMethod;
	}

	public Long getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Long delayTime) {
		this.delayTime = delayTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private String hookMethod;
	private Long delayTime;
	private Date createTime;
}
