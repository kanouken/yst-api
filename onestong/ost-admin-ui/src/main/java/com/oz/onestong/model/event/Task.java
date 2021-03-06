package com.oz.onestong.model.event;

import java.util.Date;
import java.util.List;

import com.oz.onestong.model.comment.Comment;
import com.oz.onestong.model.resources.Resource;
/**
 * 任务模型 
 * @author  xnq
 *
 */
public class Task {
	
	//附件 图片
	private List<Resource> files;
	
	
    public List<Resource> getFiles() {
		return files;
	}

	public void setFiles(List<Resource> files) {
		this.files = files;
	}
	//评论内容
	private List<Comment> comments;
	
	

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.taskId
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String taskid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.creator
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.updator
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String updator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.create_time
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.update_time
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.state
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private Byte state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.valid
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private Byte valid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.user_id
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.plan_finish_date
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private Date planFinishDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.fact_finish_date
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private Date factFinishDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.task_desc
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String taskDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.title
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.delay_date
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private Integer delayDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.comment_count
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private Integer commentCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.like_count
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private Integer likeCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.longitude
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String longitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.latitude
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String latitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.optional1
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String optional1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.optional2
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String optional2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.optional3
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String optional3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task.publish_address
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    private String publishAddress;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.taskId
     *
     * @return the value of task.taskId
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.taskId
     *
     * @param taskid the value for task.taskId
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.creator
     *
     * @return the value of task.creator
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.creator
     *
     * @param creator the value for task.creator
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.updator
     *
     * @return the value of task.updator
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.updator
     *
     * @param updator the value for task.updator
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.create_time
     *
     * @return the value of task.create_time
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.create_time
     *
     * @param createTime the value for task.create_time
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.update_time
     *
     * @return the value of task.update_time
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.update_time
     *
     * @param updateTime the value for task.update_time
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.state
     *
     * @return the value of task.state
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.state
     *
     * @param state the value for task.state
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.valid
     *
     * @return the value of task.valid
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.valid
     *
     * @param valid the value for task.valid
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.user_id
     *
     * @return the value of task.user_id
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.user_id
     *
     * @param userId the value for task.user_id
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.plan_finish_date
     *
     * @return the value of task.plan_finish_date
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public Date getPlanFinishDate() {
        return planFinishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.plan_finish_date
     *
     * @param planFinishDate the value for task.plan_finish_date
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setPlanFinishDate(Date planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.fact_finish_date
     *
     * @return the value of task.fact_finish_date
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public Date getFactFinishDate() {
        return factFinishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.fact_finish_date
     *
     * @param factFinishDate the value for task.fact_finish_date
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setFactFinishDate(Date factFinishDate) {
        this.factFinishDate = factFinishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.task_desc
     *
     * @return the value of task.task_desc
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getTaskDesc() {
        return taskDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.task_desc
     *
     * @param taskDesc the value for task.task_desc
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc == null ? null : taskDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.title
     *
     * @return the value of task.title
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.title
     *
     * @param title the value for task.title
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.delay_date
     *
     * @return the value of task.delay_date
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public Integer getDelayDate() {
        return delayDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.delay_date
     *
     * @param delayDate the value for task.delay_date
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setDelayDate(Integer delayDate) {
        this.delayDate = delayDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.comment_count
     *
     * @return the value of task.comment_count
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.comment_count
     *
     * @param commentCount the value for task.comment_count
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.like_count
     *
     * @return the value of task.like_count
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.like_count
     *
     * @param likeCount the value for task.like_count
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.longitude
     *
     * @return the value of task.longitude
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.longitude
     *
     * @param longitude the value for task.longitude
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.latitude
     *
     * @return the value of task.latitude
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.latitude
     *
     * @param latitude the value for task.latitude
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.optional1
     *
     * @return the value of task.optional1
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getOptional1() {
        return optional1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.optional1
     *
     * @param optional1 the value for task.optional1
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setOptional1(String optional1) {
        this.optional1 = optional1 == null ? null : optional1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.optional2
     *
     * @return the value of task.optional2
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.optional2
     *
     * @param optional2 the value for task.optional2
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setOptional2(String optional2) {
        this.optional2 = optional2 == null ? null : optional2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.optional3
     *
     * @return the value of task.optional3
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getOptional3() {
        return optional3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.optional3
     *
     * @param optional3 the value for task.optional3
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setOptional3(String optional3) {
        this.optional3 = optional3 == null ? null : optional3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task.publish_address
     *
     * @return the value of task.publish_address
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public String getPublishAddress() {
        return publishAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task.publish_address
     *
     * @param publishAddress the value for task.publish_address
     *
     * @mbggenerated Mon Apr 13 10:23:37 CST 2015
     */
    public void setPublishAddress(String publishAddress) {
        this.publishAddress = publishAddress == null ? null : publishAddress.trim();
    }
}