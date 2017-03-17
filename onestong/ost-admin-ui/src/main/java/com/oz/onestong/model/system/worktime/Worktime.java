package com.oz.onestong.model.system.worktime;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oz.onestong.tools.CustomerDataJsonConvertor;

public class Worktime {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.wt_id
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private Integer wtId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.startwork_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private Date startworkTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.offwork_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private Date offworkTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.create_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.update_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.valid
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private Byte valid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.creator
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.updator
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private String updator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.optional1
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private String optional1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.optional2
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private String optional2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.optional3
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private String optional3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column worktime.domain_id
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    private Integer domainId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.wt_id
     *
     * @return the value of worktime.wt_id
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public Integer getWtId() {
        return wtId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.wt_id
     *
     * @param wtId the value for worktime.wt_id
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setWtId(Integer wtId) {
        this.wtId = wtId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.startwork_time
     *
     * @return the value of worktime.startwork_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    @JsonSerialize(using=CustomerDataJsonConvertor.class)
    public Date getStartworkTime() {
        return startworkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.startwork_time
     *
     * @param startworkTime the value for worktime.startwork_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setStartworkTime(Date startworkTime) {
        this.startworkTime = startworkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.offwork_time
     *
     * @return the value of worktime.offwork_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    @JsonSerialize(using=CustomerDataJsonConvertor.class)
    public Date getOffworkTime() {
        return offworkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.offwork_time
     *
     * @param offworkTime the value for worktime.offwork_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setOffworkTime(Date offworkTime) {
        this.offworkTime = offworkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.create_time
     *
     * @return the value of worktime.create_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.create_time
     *
     * @param createTime the value for worktime.create_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.update_time
     *
     * @return the value of worktime.update_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.update_time
     *
     * @param updateTime the value for worktime.update_time
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.valid
     *
     * @return the value of worktime.valid
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.valid
     *
     * @param valid the value for worktime.valid
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.creator
     *
     * @return the value of worktime.creator
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.creator
     *
     * @param creator the value for worktime.creator
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.updator
     *
     * @return the value of worktime.updator
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.updator
     *
     * @param updator the value for worktime.updator
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.optional1
     *
     * @return the value of worktime.optional1
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public String getOptional1() {
        return optional1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.optional1
     *
     * @param optional1 the value for worktime.optional1
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setOptional1(String optional1) {
        this.optional1 = optional1 == null ? null : optional1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.optional2
     *
     * @return the value of worktime.optional2
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.optional2
     *
     * @param optional2 the value for worktime.optional2
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setOptional2(String optional2) {
        this.optional2 = optional2 == null ? null : optional2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.optional3
     *
     * @return the value of worktime.optional3
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public String getOptional3() {
        return optional3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.optional3
     *
     * @param optional3 the value for worktime.optional3
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setOptional3(String optional3) {
        this.optional3 = optional3 == null ? null : optional3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column worktime.domain_id
     *
     * @return the value of worktime.domain_id
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public Integer getDomainId() {
        return domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column worktime.domain_id
     *
     * @param domainId the value for worktime.domain_id
     *
     * @mbggenerated Mon Jan 26 13:21:02 CST 2015
     */
    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }
}