package org.ost.crm.model.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.ost.entity.base.BaseEntity;

@Entity
@Table(name = "tbl_common_param")
public class CommonParams extends BaseEntity {
	private String type;
	
	@Column(name="paramKey")
	private String key;
	@Column(name="paramVal")
	private String val;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
