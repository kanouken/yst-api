package org.ost.edge.onestong.model.domain;

import java.util.Date;

import org.ost.edge.onestong.tools.Constants;

public class DomainOrderDetail {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.od_id
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private Integer odId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.order_id
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private Integer orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.create_time
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.update_time
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.od_sum
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private Integer odSum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.optional1
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private String optional1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.optional2
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private String optional2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.optional3
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private String optional3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.valid
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private Byte valid = Constants.DATA_VALID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.creator
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders_detail.updator
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    private String updator;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.od_id
     *
     * @return the value of orders_detail.od_id
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public Integer getOdId() {
        return odId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.od_id
     *
     * @param odId the value for orders_detail.od_id
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setOdId(Integer odId) {
        this.odId = odId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.order_id
     *
     * @return the value of orders_detail.order_id
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.order_id
     *
     * @param orderId the value for orders_detail.order_id
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.create_time
     *
     * @return the value of orders_detail.create_time
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.create_time
     *
     * @param createTime the value for orders_detail.create_time
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.update_time
     *
     * @return the value of orders_detail.update_time
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.update_time
     *
     * @param updateTime the value for orders_detail.update_time
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.od_sum
     *
     * @return the value of orders_detail.od_sum
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public Integer getOdSum() {
        return odSum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.od_sum
     *
     * @param odSum the value for orders_detail.od_sum
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setOdSum(Integer odSum) {
        this.odSum = odSum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.optional1
     *
     * @return the value of orders_detail.optional1
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public String getOptional1() {
        return optional1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.optional1
     *
     * @param optional1 the value for orders_detail.optional1
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setOptional1(String optional1) {
        this.optional1 = optional1 == null ? null : optional1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.optional2
     *
     * @return the value of orders_detail.optional2
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.optional2
     *
     * @param optional2 the value for orders_detail.optional2
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setOptional2(String optional2) {
        this.optional2 = optional2 == null ? null : optional2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.optional3
     *
     * @return the value of orders_detail.optional3
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public String getOptional3() {
        return optional3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.optional3
     *
     * @param optional3 the value for orders_detail.optional3
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setOptional3(String optional3) {
        this.optional3 = optional3 == null ? null : optional3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.valid
     *
     * @return the value of orders_detail.valid
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.valid
     *
     * @param valid the value for orders_detail.valid
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.creator
     *
     * @return the value of orders_detail.creator
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.creator
     *
     * @param creator the value for orders_detail.creator
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders_detail.updator
     *
     * @return the value of orders_detail.updator
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders_detail.updator
     *
     * @param updator the value for orders_detail.updator
     *
     * @mbggenerated Sun Jan 11 13:14:45 CST 2015
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }
}