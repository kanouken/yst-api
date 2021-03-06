package com.oz.onestong.model.event;

import java.util.Date;

public class TaskItem {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.task_item_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private Integer taskItemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.task_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private String taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.creator_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private Integer creatorId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.excutor_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private Integer excutorId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.create_time
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.update_time
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.state
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private Byte state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.valid
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private Byte valid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.creator
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.updator
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private String updator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.optional1
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private String optional1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.optional2
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private String optional2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.optional3
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private String optional3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.task_item_desc
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private String taskItemDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_item.excutor_name
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    private String excutorName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.task_item_id
     *
     * @return the value of task_item.task_item_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public Integer getTaskItemId() {
        return taskItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.task_item_id
     *
     * @param taskItemId the value for task_item.task_item_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setTaskItemId(Integer taskItemId) {
        this.taskItemId = taskItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.task_id
     *
     * @return the value of task_item.task_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.task_id
     *
     * @param taskId the value for task_item.task_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.creator_id
     *
     * @return the value of task_item.creator_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.creator_id
     *
     * @param creatorId the value for task_item.creator_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.excutor_id
     *
     * @return the value of task_item.excutor_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public Integer getExcutorId() {
        return excutorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.excutor_id
     *
     * @param excutorId the value for task_item.excutor_id
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setExcutorId(Integer excutorId) {
        this.excutorId = excutorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.create_time
     *
     * @return the value of task_item.create_time
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.create_time
     *
     * @param createTime the value for task_item.create_time
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.update_time
     *
     * @return the value of task_item.update_time
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.update_time
     *
     * @param updateTime the value for task_item.update_time
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.state
     *
     * @return the value of task_item.state
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.state
     *
     * @param state the value for task_item.state
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.valid
     *
     * @return the value of task_item.valid
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.valid
     *
     * @param valid the value for task_item.valid
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.creator
     *
     * @return the value of task_item.creator
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.creator
     *
     * @param creator the value for task_item.creator
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.updator
     *
     * @return the value of task_item.updator
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.updator
     *
     * @param updator the value for task_item.updator
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.optional1
     *
     * @return the value of task_item.optional1
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public String getOptional1() {
        return optional1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.optional1
     *
     * @param optional1 the value for task_item.optional1
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setOptional1(String optional1) {
        this.optional1 = optional1 == null ? null : optional1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.optional2
     *
     * @return the value of task_item.optional2
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.optional2
     *
     * @param optional2 the value for task_item.optional2
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setOptional2(String optional2) {
        this.optional2 = optional2 == null ? null : optional2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.optional3
     *
     * @return the value of task_item.optional3
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public String getOptional3() {
        return optional3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.optional3
     *
     * @param optional3 the value for task_item.optional3
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setOptional3(String optional3) {
        this.optional3 = optional3 == null ? null : optional3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.task_item_desc
     *
     * @return the value of task_item.task_item_desc
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public String getTaskItemDesc() {
        return taskItemDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.task_item_desc
     *
     * @param taskItemDesc the value for task_item.task_item_desc
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setTaskItemDesc(String taskItemDesc) {
        this.taskItemDesc = taskItemDesc == null ? null : taskItemDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_item.excutor_name
     *
     * @return the value of task_item.excutor_name
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public String getExcutorName() {
        return excutorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_item.excutor_name
     *
     * @param excutorName the value for task_item.excutor_name
     *
     * @mbggenerated Sun Apr 19 19:53:25 CST 2015
     */
    public void setExcutorName(String excutorName) {
        this.excutorName = excutorName == null ? null : excutorName.trim();
    }
}