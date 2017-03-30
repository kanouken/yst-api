package com.oz.onestong.model.event;

import java.util.Date;
import java.util.List;

import com.oz.onestong.model.comment.Comment;
import com.oz.onestong.model.resources.Resource;
import com.oz.onestong.model.tag.CTag;
import com.oz.onestong.model.tag.PTag;
import com.oz.onestong.tools.Constants;

public class VisitEvent {
	
	private List<Resource> files;
	
	private List<PTag> pTags;
	private List<CTag> cTags;
	public List<Resource> getFiles() {
		return files;
	}

	public void setFiles(List<Resource> files) {
		this.files = files;
	}

	public List<PTag> getpTags() {
		return pTags;
	}

	public void setpTags(List<PTag> pTags) {
		this.pTags = pTags;
	}

	public List<CTag> getcTags() {
		return cTags;
	}

	public void setcTags(List<CTag> cTags) {
		this.cTags = cTags;
	}

	/**
	 * 评论
	 */
	private List<Comment> comments;
    public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.ve_id
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String veId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.user_id
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.customer_id
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private Integer customerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.title
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.content
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.visited_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private Date visitedTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.visted_address
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String vistedAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.visted_longitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String vistedLongitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.visited_latitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String visitedLatitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.visitout_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private Date visitoutTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.visitout_address
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String visitoutAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.visitout_longitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String visitoutLongitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.visitout_latitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String visitoutLatitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.state
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private Byte state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.create_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.update_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.creator
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.updator
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String updator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.valid
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private Byte valid  =Constants.DATA_VALID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.optional1
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String optional1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.optional2
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String optional2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.optional3
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String optional3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.visit_summary
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String visitSummary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.visitout_title
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private String visitoutTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.like_count
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private Integer likeCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column visit_events.comment_count
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    private Integer commentCount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.ve_id
     *
     * @return the value of visit_events.ve_id
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getVeId() {
        return veId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.ve_id
     *
     * @param veId the value for visit_events.ve_id
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVeId(String veId) {
        this.veId = veId == null ? null : veId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.user_id
     *
     * @return the value of visit_events.user_id
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.user_id
     *
     * @param userId the value for visit_events.user_id
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.customer_id
     *
     * @return the value of visit_events.customer_id
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.customer_id
     *
     * @param customerId the value for visit_events.customer_id
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.title
     *
     * @return the value of visit_events.title
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.title
     *
     * @param title the value for visit_events.title
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.content
     *
     * @return the value of visit_events.content
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.content
     *
     * @param content the value for visit_events.content
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.visited_time
     *
     * @return the value of visit_events.visited_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public Date getVisitedTime() {
        return visitedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.visited_time
     *
     * @param visitedTime the value for visit_events.visited_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVisitedTime(Date visitedTime) {
        this.visitedTime = visitedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.visted_address
     *
     * @return the value of visit_events.visted_address
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getVistedAddress() {
        return vistedAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.visted_address
     *
     * @param vistedAddress the value for visit_events.visted_address
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVistedAddress(String vistedAddress) {
        this.vistedAddress = vistedAddress == null ? null : vistedAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.visted_longitude
     *
     * @return the value of visit_events.visted_longitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getVistedLongitude() {
        return vistedLongitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.visted_longitude
     *
     * @param vistedLongitude the value for visit_events.visted_longitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVistedLongitude(String vistedLongitude) {
        this.vistedLongitude = vistedLongitude == null ? null : vistedLongitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.visited_latitude
     *
     * @return the value of visit_events.visited_latitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getVisitedLatitude() {
        return visitedLatitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.visited_latitude
     *
     * @param visitedLatitude the value for visit_events.visited_latitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVisitedLatitude(String visitedLatitude) {
        this.visitedLatitude = visitedLatitude == null ? null : visitedLatitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.visitout_time
     *
     * @return the value of visit_events.visitout_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public Date getVisitoutTime() {
        return visitoutTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.visitout_time
     *
     * @param visitoutTime the value for visit_events.visitout_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVisitoutTime(Date visitoutTime) {
        this.visitoutTime = visitoutTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.visitout_address
     *
     * @return the value of visit_events.visitout_address
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getVisitoutAddress() {
        return visitoutAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.visitout_address
     *
     * @param visitoutAddress the value for visit_events.visitout_address
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVisitoutAddress(String visitoutAddress) {
        this.visitoutAddress = visitoutAddress == null ? null : visitoutAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.visitout_longitude
     *
     * @return the value of visit_events.visitout_longitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getVisitoutLongitude() {
        return visitoutLongitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.visitout_longitude
     *
     * @param visitoutLongitude the value for visit_events.visitout_longitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVisitoutLongitude(String visitoutLongitude) {
        this.visitoutLongitude = visitoutLongitude == null ? null : visitoutLongitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.visitout_latitude
     *
     * @return the value of visit_events.visitout_latitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getVisitoutLatitude() {
        return visitoutLatitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.visitout_latitude
     *
     * @param visitoutLatitude the value for visit_events.visitout_latitude
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVisitoutLatitude(String visitoutLatitude) {
        this.visitoutLatitude = visitoutLatitude == null ? null : visitoutLatitude.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.state
     *
     * @return the value of visit_events.state
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.state
     *
     * @param state the value for visit_events.state
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.create_time
     *
     * @return the value of visit_events.create_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.create_time
     *
     * @param createTime the value for visit_events.create_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.update_time
     *
     * @return the value of visit_events.update_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.update_time
     *
     * @param updateTime the value for visit_events.update_time
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.creator
     *
     * @return the value of visit_events.creator
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.creator
     *
     * @param creator the value for visit_events.creator
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.updator
     *
     * @return the value of visit_events.updator
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.updator
     *
     * @param updator the value for visit_events.updator
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.valid
     *
     * @return the value of visit_events.valid
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.valid
     *
     * @param valid the value for visit_events.valid
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.optional1
     *
     * @return the value of visit_events.optional1
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getOptional1() {
        return optional1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.optional1
     *
     * @param optional1 the value for visit_events.optional1
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setOptional1(String optional1) {
        this.optional1 = optional1 == null ? null : optional1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.optional2
     *
     * @return the value of visit_events.optional2
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getOptional2() {
        return optional2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.optional2
     *
     * @param optional2 the value for visit_events.optional2
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setOptional2(String optional2) {
        this.optional2 = optional2 == null ? null : optional2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.optional3
     *
     * @return the value of visit_events.optional3
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getOptional3() {
        return optional3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.optional3
     *
     * @param optional3 the value for visit_events.optional3
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setOptional3(String optional3) {
        this.optional3 = optional3 == null ? null : optional3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.visit_summary
     *
     * @return the value of visit_events.visit_summary
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getVisitSummary() {
        return visitSummary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.visit_summary
     *
     * @param visitSummary the value for visit_events.visit_summary
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVisitSummary(String visitSummary) {
        this.visitSummary = visitSummary == null ? null : visitSummary.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.visitout_title
     *
     * @return the value of visit_events.visitout_title
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public String getVisitoutTitle() {
        return visitoutTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.visitout_title
     *
     * @param visitoutTitle the value for visit_events.visitout_title
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setVisitoutTitle(String visitoutTitle) {
        this.visitoutTitle = visitoutTitle == null ? null : visitoutTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.like_count
     *
     * @return the value of visit_events.like_count
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.like_count
     *
     * @param likeCount the value for visit_events.like_count
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column visit_events.comment_count
     *
     * @return the value of visit_events.comment_count
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column visit_events.comment_count
     *
     * @param commentCount the value for visit_events.comment_count
     *
     * @mbggenerated Wed Jan 14 10:38:36 CST 2015
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}