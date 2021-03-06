package org.ost.edge.onestong.model.tag;

import java.util.Date;

import org.ost.edge.onestong.tools.Constants;

public class CTag {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.c_t_id
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private Integer cTId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.c_t_name
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private String cTName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.create_time
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.update_time
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.valid
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private Byte valid = Constants.DATA_VALID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.creator
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.updator
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private String updator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.optional1
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private String optional1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.optional2
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private String optional2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.optional3
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private String optional3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tags.domain_id
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    private Integer domainId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.c_t_id
     *
     * @return the value of c_tags.c_t_id
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public Integer getcTId() {
        return cTId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.c_t_id
     *
     * @param cTId the value for c_tags.c_t_id
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setcTId(Integer cTId) {
        this.cTId = cTId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.c_t_name
     *
     * @return the value of c_tags.c_t_name
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public String getcTName() {
        return cTName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.c_t_name
     *
     * @param cTName the value for c_tags.c_t_name
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setcTName(String cTName) {
        this.cTName = cTName == null ? null : cTName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.create_time
     *
     * @return the value of c_tags.create_time
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.create_time
     *
     * @param createTime the value for c_tags.create_time
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.update_time
     *
     * @return the value of c_tags.update_time
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.update_time
     *
     * @param updateTime the value for c_tags.update_time
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.valid
     *
     * @return the value of c_tags.valid
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.valid
     *
     * @param valid the value for c_tags.valid
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.creator
     *
     * @return the value of c_tags.creator
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.creator
     *
     * @param creator the value for c_tags.creator
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.updator
     *
     * @return the value of c_tags.updator
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.updator
     *
     * @param updator the value for c_tags.updator
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.optional1
     *
     * @return the value of c_tags.optional1
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public String getOptional1() {
        return optional1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.optional1
     *
     * @param optional1 the value for c_tags.optional1
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setOptional1(String optional1) {
        this.optional1 = optional1 == null ? null : optional1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.optional2
     *
     * @return the value of c_tags.optional2
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.optional2
     *
     * @param optional2 the value for c_tags.optional2
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setOptional2(String optional2) {
        this.optional2 = optional2 == null ? null : optional2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.optional3
     *
     * @return the value of c_tags.optional3
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public String getOptional3() {
        return optional3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.optional3
     *
     * @param optional3 the value for c_tags.optional3
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setOptional3(String optional3) {
        this.optional3 = optional3 == null ? null : optional3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tags.domain_id
     *
     * @return the value of c_tags.domain_id
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public Integer getDomainId() {
        return domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tags.domain_id
     *
     * @param domainId the value for c_tags.domain_id
     *
     * @mbggenerated Mon Jan 12 11:49:25 CST 2015
     */
    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }
}