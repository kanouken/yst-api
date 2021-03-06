package org.ost.edge.onestong.model.system.location;

import java.util.Date;

import org.ost.edge.onestong.tools.Constants;

public class AttenceLocation {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.location_id
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private Integer locationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.attence_latitude
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private String attenceLatitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.attence_longitude
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private String attenceLongitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.attence_address
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private String attenceAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.allow_distance
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private String allowDistance;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.create_time
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.update_time
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.creator
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.updator
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private String updator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.valid
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private Byte valid = Constants.DATA_VALID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.optional1
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private String optional1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.optional2
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private String optional2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.optional3
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private String optional3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attence_location.domain_id
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    private Integer domainId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.location_id
     *
     * @return the value of attence_location.location_id
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.location_id
     *
     * @param locationId the value for attence_location.location_id
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.attence_latitude
     *
     * @return the value of attence_location.attence_latitude
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public String getAttenceLatitude() {
        return attenceLatitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.attence_latitude
     *
     * @param attenceLatitude the value for attence_location.attence_latitude
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setAttenceLatitude(String attenceLatitude) {
        this.attenceLatitude = attenceLatitude == null ? null : attenceLatitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.attence_longitude
     *
     * @return the value of attence_location.attence_longitude
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public String getAttenceLongitude() {
        return attenceLongitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.attence_longitude
     *
     * @param attenceLongitude the value for attence_location.attence_longitude
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setAttenceLongitude(String attenceLongitude) {
        this.attenceLongitude = attenceLongitude == null ? null : attenceLongitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.attence_address
     *
     * @return the value of attence_location.attence_address
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public String getAttenceAddress() {
        return attenceAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.attence_address
     *
     * @param attenceAddress the value for attence_location.attence_address
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setAttenceAddress(String attenceAddress) {
        this.attenceAddress = attenceAddress == null ? null : attenceAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.allow_distance
     *
     * @return the value of attence_location.allow_distance
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public String getAllowDistance() {
        return allowDistance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.allow_distance
     *
     * @param allowDistance the value for attence_location.allow_distance
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setAllowDistance(String allowDistance) {
        this.allowDistance = allowDistance == null ? null : allowDistance.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.create_time
     *
     * @return the value of attence_location.create_time
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.create_time
     *
     * @param createTime the value for attence_location.create_time
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.update_time
     *
     * @return the value of attence_location.update_time
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.update_time
     *
     * @param updateTime the value for attence_location.update_time
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.creator
     *
     * @return the value of attence_location.creator
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.creator
     *
     * @param creator the value for attence_location.creator
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.updator
     *
     * @return the value of attence_location.updator
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.updator
     *
     * @param updator the value for attence_location.updator
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.valid
     *
     * @return the value of attence_location.valid
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.valid
     *
     * @param valid the value for attence_location.valid
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.optional1
     *
     * @return the value of attence_location.optional1
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public String getOptional1() {
        return optional1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.optional1
     *
     * @param optional1 the value for attence_location.optional1
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setOptional1(String optional1) {
        this.optional1 = optional1 == null ? null : optional1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.optional2
     *
     * @return the value of attence_location.optional2
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.optional2
     *
     * @param optional2 the value for attence_location.optional2
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setOptional2(String optional2) {
        this.optional2 = optional2 == null ? null : optional2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.optional3
     *
     * @return the value of attence_location.optional3
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public String getOptional3() {
        return optional3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.optional3
     *
     * @param optional3 the value for attence_location.optional3
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setOptional3(String optional3) {
        this.optional3 = optional3 == null ? null : optional3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column attence_location.domain_id
     *
     * @return the value of attence_location.domain_id
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public Integer getDomainId() {
        return domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column attence_location.domain_id
     *
     * @param domainId the value for attence_location.domain_id
     *
     * @mbggenerated Wed Jun 17 17:49:56 CST 2015
     */
    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }
}