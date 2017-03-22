package com.oz.onestong.model.system.ibeacon;

import java.util.Date;

public class IbeaconInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.ibeacon_id
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private Integer ibeaconId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.department_id
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private Integer departmentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.ibeacon_uuid
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String ibeaconUuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.ibeancon_major
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String ibeanconMajor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.ibeancon_minor
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String ibeanconMinor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.longitude
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String longitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.latitude
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String latitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.location_name
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String locationName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.address
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.creator
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.updator
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String updator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.create_time
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.update_time
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.optional1
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String optional1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.optional2
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String optional2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ibeacon_info.optional3
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    private String optional3;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.ibeacon_id
     *
     * @return the value of ibeacon_info.ibeacon_id
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public Integer getIbeaconId() {
        return ibeaconId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.ibeacon_id
     *
     * @param ibeaconId the value for ibeacon_info.ibeacon_id
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setIbeaconId(Integer ibeaconId) {
        this.ibeaconId = ibeaconId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.department_id
     *
     * @return the value of ibeacon_info.department_id
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.department_id
     *
     * @param departmentId the value for ibeacon_info.department_id
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.ibeacon_uuid
     *
     * @return the value of ibeacon_info.ibeacon_uuid
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getIbeaconUuid() {
        return ibeaconUuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.ibeacon_uuid
     *
     * @param ibeaconUuid the value for ibeacon_info.ibeacon_uuid
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setIbeaconUuid(String ibeaconUuid) {
        this.ibeaconUuid = ibeaconUuid == null ? null : ibeaconUuid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.ibeancon_major
     *
     * @return the value of ibeacon_info.ibeancon_major
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getIbeanconMajor() {
        return ibeanconMajor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.ibeancon_major
     *
     * @param ibeanconMajor the value for ibeacon_info.ibeancon_major
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setIbeanconMajor(String ibeanconMajor) {
        this.ibeanconMajor = ibeanconMajor == null ? null : ibeanconMajor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.ibeancon_minor
     *
     * @return the value of ibeacon_info.ibeancon_minor
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getIbeanconMinor() {
        return ibeanconMinor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.ibeancon_minor
     *
     * @param ibeanconMinor the value for ibeacon_info.ibeancon_minor
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setIbeanconMinor(String ibeanconMinor) {
        this.ibeanconMinor = ibeanconMinor == null ? null : ibeanconMinor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.longitude
     *
     * @return the value of ibeacon_info.longitude
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.longitude
     *
     * @param longitude the value for ibeacon_info.longitude
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.latitude
     *
     * @return the value of ibeacon_info.latitude
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.latitude
     *
     * @param latitude the value for ibeacon_info.latitude
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.location_name
     *
     * @return the value of ibeacon_info.location_name
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.location_name
     *
     * @param locationName the value for ibeacon_info.location_name
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName == null ? null : locationName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.address
     *
     * @return the value of ibeacon_info.address
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.address
     *
     * @param address the value for ibeacon_info.address
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.creator
     *
     * @return the value of ibeacon_info.creator
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.creator
     *
     * @param creator the value for ibeacon_info.creator
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.updator
     *
     * @return the value of ibeacon_info.updator
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.updator
     *
     * @param updator the value for ibeacon_info.updator
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.create_time
     *
     * @return the value of ibeacon_info.create_time
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.create_time
     *
     * @param createTime the value for ibeacon_info.create_time
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.update_time
     *
     * @return the value of ibeacon_info.update_time
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.update_time
     *
     * @param updateTime the value for ibeacon_info.update_time
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.optional1
     *
     * @return the value of ibeacon_info.optional1
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getOptional1() {
        return optional1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.optional1
     *
     * @param optional1 the value for ibeacon_info.optional1
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setOptional1(String optional1) {
        this.optional1 = optional1 == null ? null : optional1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.optional2
     *
     * @return the value of ibeacon_info.optional2
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.optional2
     *
     * @param optional2 the value for ibeacon_info.optional2
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setOptional2(String optional2) {
        this.optional2 = optional2 == null ? null : optional2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ibeacon_info.optional3
     *
     * @return the value of ibeacon_info.optional3
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public String getOptional3() {
        return optional3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ibeacon_info.optional3
     *
     * @param optional3 the value for ibeacon_info.optional3
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    public void setOptional3(String optional3) {
        this.optional3 = optional3 == null ? null : optional3.trim();
    }

	@Override
	public String toString() {
		return "IbeaconInfo [ibeaconId=" + ibeaconId + ", departmentId="
				+ departmentId + ", ibeaconUuid=" + ibeaconUuid
				+ ", ibeanconMajor=" + ibeanconMajor + ", ibeanconMinor="
				+ ibeanconMinor + ", longitude=" + longitude + ", latitude="
				+ latitude + ", locationName=" + locationName + ", address="
				+ address + ", creator=" + creator + ", updator=" + updator
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", optional1=" + optional1 + ", optional2=" + optional2
				+ ", optional3=" + optional3 + "]";
	}
    
    public static void main(String[] args) {
		System.out.println(new IbeaconInfo());
	}
    
}