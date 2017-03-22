package com.oz.onestong.model.resources;

import java.util.Date;

import com.oz.onestong.tools.Constants;

public class Resource {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.resource_id
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private Integer resourceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.name
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.uri
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private String uri;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.type
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private Byte type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.valid
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private Byte valid = Constants.DATA_VALID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.create_time
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.update_time
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.creator
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.updator
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private String updator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.optional1
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private String optional1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.optional2
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private String optional2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.optional3
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private String optional3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.name1
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private String name1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.name2
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private String name2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column resources.name3
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    private String name3;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.resource_id
     *
     * @return the value of resources.resource_id
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public Integer getResourceId() {
        return resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.resource_id
     *
     * @param resourceId the value for resources.resource_id
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.name
     *
     * @return the value of resources.name
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.name
     *
     * @param name the value for resources.name
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.uri
     *
     * @return the value of resources.uri
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public String getUri() {
        return uri;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.uri
     *
     * @param uri the value for resources.uri
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.type
     *
     * @return the value of resources.type
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.type
     *
     * @param type the value for resources.type
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.valid
     *
     * @return the value of resources.valid
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.valid
     *
     * @param valid the value for resources.valid
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.create_time
     *
     * @return the value of resources.create_time
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.create_time
     *
     * @param createTime the value for resources.create_time
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.update_time
     *
     * @return the value of resources.update_time
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.update_time
     *
     * @param updateTime the value for resources.update_time
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.creator
     *
     * @return the value of resources.creator
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.creator
     *
     * @param creator the value for resources.creator
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.updator
     *
     * @return the value of resources.updator
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.updator
     *
     * @param updator the value for resources.updator
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.optional1
     *
     * @return the value of resources.optional1
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public String getOptional1() {
        return optional1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.optional1
     *
     * @param optional1 the value for resources.optional1
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setOptional1(String optional1) {
        this.optional1 = optional1 == null ? null : optional1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.optional2
     *
     * @return the value of resources.optional2
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.optional2
     *
     * @param optional2 the value for resources.optional2
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setOptional2(String optional2) {
        this.optional2 = optional2 == null ? null : optional2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.optional3
     *
     * @return the value of resources.optional3
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public String getOptional3() {
        return optional3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.optional3
     *
     * @param optional3 the value for resources.optional3
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setOptional3(String optional3) {
        this.optional3 = optional3 == null ? null : optional3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.name1
     *
     * @return the value of resources.name1
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public String getName1() {
        return name1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.name1
     *
     * @param name1 the value for resources.name1
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setName1(String name1) {
        this.name1 = name1 == null ? null : name1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.name2
     *
     * @return the value of resources.name2
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public String getName2() {
        return name2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.name2
     *
     * @param name2 the value for resources.name2
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setName2(String name2) {
        this.name2 = name2 == null ? null : name2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column resources.name3
     *
     * @return the value of resources.name3
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public String getName3() {
        return name3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column resources.name3
     *
     * @param name3 the value for resources.name3
     *
     * @mbggenerated Tue Jan 13 13:47:37 CST 2015
     */
    public void setName3(String name3) {
        this.name3 = name3 == null ? null : name3.trim();
    }
}