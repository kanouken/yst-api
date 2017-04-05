package org.ost.crm.model.auth;

import java.io.Serializable;
import java.util.Date;


public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5926574067811338786L;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.role_id
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private Integer roleId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.role_name
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private String roleName;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.role_desc
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private String roleDesc;

	private String roleCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.create_time
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private Date createTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.update_time
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private Date updateTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.creator
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private String creator;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.updator
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private String updator;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.valid
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private Byte valid;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.state
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private Byte state;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.level
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private Byte level;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.optional1
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private String optional1;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.optional2
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private String optional2;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.optional3
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private String optional3;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column roles.domain_id
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	private String domainId;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.role_id
	 *
	 * @return the value of roles.role_id
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.role_id
	 *
	 * @param roleId
	 *            the value for roles.role_id
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.role_name
	 *
	 * @return the value of roles.role_name
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.role_name
	 *
	 * @param roleName
	 *            the value for roles.role_name
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName == null ? null : roleName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.role_desc
	 *
	 * @return the value of roles.role_desc
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.role_desc
	 *
	 * @param roleDesc
	 *            the value for roles.role_desc
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc == null ? null : roleDesc.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.create_time
	 *
	 * @return the value of roles.create_time
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.create_time
	 *
	 * @param createTime
	 *            the value for roles.create_time
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.update_time
	 *
	 * @return the value of roles.update_time
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.update_time
	 *
	 * @param updateTime
	 *            the value for roles.update_time
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.creator
	 *
	 * @return the value of roles.creator
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.creator
	 *
	 * @param creator
	 *            the value for roles.creator
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.updator
	 *
	 * @return the value of roles.updator
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public String getUpdator() {
		return updator;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.updator
	 *
	 * @param updator
	 *            the value for roles.updator
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setUpdator(String updator) {
		this.updator = updator == null ? null : updator.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.valid
	 *
	 * @return the value of roles.valid
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public Byte getValid() {
		return valid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.valid
	 *
	 * @param valid
	 *            the value for roles.valid
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setValid(Byte valid) {
		this.valid = valid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.state
	 *
	 * @return the value of roles.state
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public Byte getState() {
		return state;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.state
	 *
	 * @param state
	 *            the value for roles.state
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setState(Byte state) {
		this.state = state;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.level
	 *
	 * @return the value of roles.level
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public Byte getLevel() {
		return level;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.level
	 *
	 * @param level
	 *            the value for roles.level
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setLevel(Byte level) {
		this.level = level;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.optional1
	 *
	 * @return the value of roles.optional1
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public String getOptional1() {
		return optional1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.optional1
	 *
	 * @param optional1
	 *            the value for roles.optional1
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setOptional1(String optional1) {
		this.optional1 = optional1 == null ? null : optional1.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.optional2
	 *
	 * @return the value of roles.optional2
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public String getOptional2() {
		return optional2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.optional2
	 *
	 * @param optional2
	 *            the value for roles.optional2
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setOptional2(String optional2) {
		this.optional2 = optional2 == null ? null : optional2.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.optional3
	 *
	 * @return the value of roles.optional3
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public String getOptional3() {
		return optional3;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.optional3
	 *
	 * @param optional3
	 *            the value for roles.optional3
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setOptional3(String optional3) {
		this.optional3 = optional3 == null ? null : optional3.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column roles.domain_id
	 *
	 * @return the value of roles.domain_id
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public String getDomainId() {
		return domainId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column roles.domain_id
	 *
	 * @param domainId
	 *            the value for roles.domain_id
	 *
	 * @mbggenerated Fri Jan 09 16:44:02 CST 2015
	 */
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
}