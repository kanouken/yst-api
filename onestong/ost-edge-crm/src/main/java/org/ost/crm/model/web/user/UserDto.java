package org.ost.crm.model.web.user;

/**
 * 
 * @author chenyingwen
 *
 */
public class UserDto {
	private Integer userId;
	private String email;
	private String realName;
	private String deptName;
	private String domainId;
	private Integer deptId;
	private Byte isDirector;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Byte getIsDirector() {
		return isDirector;
	}

	public void setIsDirector(Byte isDirector) {
		this.isDirector = isDirector;
	}

}
