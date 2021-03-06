package com.oz.onestong.model.yixin;

import java.util.Date;
import java.util.List;

import com.oz.onestong.model.user.User;

public class Group{ 
	private List<User> members;
    public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.group_id
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private String groupId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.group_name
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private String groupName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.group_descrption
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private String groupDescrption;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.is_public
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private Byte isPublic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.approval_need
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private Byte approvalNeed;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.owner
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private Integer owner;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.creator
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.updator
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private String updator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.create_time
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.update_time
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.optional1
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private String optional1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.optional2
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private String optional2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.optional3
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private String optional3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.valid
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private Byte valid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.maxusers
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private Integer maxusers;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.domain_id
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private Integer domainId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_group.event_id
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    private String eventId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.group_id
     *
     * @return the value of im_group.group_id
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.group_id
     *
     * @param groupId the value for im_group.group_id
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.group_name
     *
     * @return the value of im_group.group_name
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.group_name
     *
     * @param groupName the value for im_group.group_name
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.group_descrption
     *
     * @return the value of im_group.group_descrption
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public String getGroupDescrption() {
        return groupDescrption;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.group_descrption
     *
     * @param groupDescrption the value for im_group.group_descrption
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setGroupDescrption(String groupDescrption) {
        this.groupDescrption = groupDescrption == null ? null : groupDescrption.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.is_public
     *
     * @return the value of im_group.is_public
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public Byte getIsPublic() {
        return isPublic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.is_public
     *
     * @param isPublic the value for im_group.is_public
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setIsPublic(Byte isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.approval_need
     *
     * @return the value of im_group.approval_need
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public Byte getApprovalNeed() {
        return approvalNeed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.approval_need
     *
     * @param approvalNeed the value for im_group.approval_need
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setApprovalNeed(Byte approvalNeed) {
        this.approvalNeed = approvalNeed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.owner
     *
     * @return the value of im_group.owner
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public Integer getOwner() {
        return owner;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.owner
     *
     * @param owner the value for im_group.owner
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.creator
     *
     * @return the value of im_group.creator
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.creator
     *
     * @param creator the value for im_group.creator
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.updator
     *
     * @return the value of im_group.updator
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.updator
     *
     * @param updator the value for im_group.updator
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.create_time
     *
     * @return the value of im_group.create_time
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.create_time
     *
     * @param createTime the value for im_group.create_time
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.update_time
     *
     * @return the value of im_group.update_time
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.update_time
     *
     * @param updateTime the value for im_group.update_time
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.optional1
     *
     * @return the value of im_group.optional1
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public String getOptional1() {
        return optional1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.optional1
     *
     * @param optional1 the value for im_group.optional1
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setOptional1(String optional1) {
        this.optional1 = optional1 == null ? null : optional1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.optional2
     *
     * @return the value of im_group.optional2
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.optional2
     *
     * @param optional2 the value for im_group.optional2
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setOptional2(String optional2) {
        this.optional2 = optional2 == null ? null : optional2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.optional3
     *
     * @return the value of im_group.optional3
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public String getOptional3() {
        return optional3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.optional3
     *
     * @param optional3 the value for im_group.optional3
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setOptional3(String optional3) {
        this.optional3 = optional3 == null ? null : optional3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.valid
     *
     * @return the value of im_group.valid
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.valid
     *
     * @param valid the value for im_group.valid
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.maxusers
     *
     * @return the value of im_group.maxusers
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public Integer getMaxusers() {
        return maxusers;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.maxusers
     *
     * @param maxusers the value for im_group.maxusers
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setMaxusers(Integer maxusers) {
        this.maxusers = maxusers;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.domain_id
     *
     * @return the value of im_group.domain_id
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public Integer getDomainId() {
        return domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.domain_id
     *
     * @param domainId the value for im_group.domain_id
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_group.event_id
     *
     * @return the value of im_group.event_id
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_group.event_id
     *
     * @param eventId the value for im_group.event_id
     *
     * @mbggenerated Thu Jan 22 13:08:38 CST 2015
     */
    public void setEventId(String eventId) {
        this.eventId = eventId == null ? null : eventId.trim();
    }
}