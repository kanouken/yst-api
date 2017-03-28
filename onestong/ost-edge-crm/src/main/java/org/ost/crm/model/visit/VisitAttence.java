package org.ost.crm.model.visit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_attence_events")
public class VisitAttence {
	@Id
	private String id;

	private Date updateTime;

	private String createBy;
	private String updateBy;
	private Byte isDelete;
	private String schemaId;
	@Transient
	private Integer createId;
	@Transient
	private Integer updateId;
	private String content;
	private Byte state;
	private Integer userID;
	private String userName;
	private Integer organizeID;
	private String organizeName;
	private Byte signInState;
	private String signInType;
	private Byte signInValid;
	private Date signInTime;
	private Integer signInDiffTime;
	private String signInAddress;
	private Double signInLat;
	private Double signInLng;
	private Byte signOutState;
	private String signOutType;
	private Byte signOutValid;
	private Date signOutTime;
	private Integer signOutDiffTime;
	private String signOutAddress;
	private Double signOutLat;
	private Double signOutLng;
	private Date createTime;

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}

	public String getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getOrganizeID() {
		return organizeID;
	}

	public void setOrganizeID(Integer organizeID) {
		this.organizeID = organizeID;
	}

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}

	public Byte getSignInState() {
		return signInState;
	}

	public void setSignInState(Byte signInState) {
		this.signInState = signInState;
	}

	public String getSignInType() {
		return signInType;
	}

	public void setSignInType(String signInType) {
		this.signInType = signInType;
	}

	public Byte getSignInValid() {
		return signInValid;
	}

	public void setSignInValid(Byte signInValid) {
		this.signInValid = signInValid;
	}

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public Integer getSignInDiffTime() {
		return signInDiffTime;
	}

	public void setSignInDiffTime(Integer signInDiffTime) {
		this.signInDiffTime = signInDiffTime;
	}

	public String getSignInAddress() {
		return signInAddress;
	}

	public void setSignInAddress(String signInAddress) {
		this.signInAddress = signInAddress;
	}

	public Double getSignInLat() {
		return signInLat;
	}

	public void setSignInLat(Double signInLat) {
		this.signInLat = signInLat;
	}

	public Double getSignInLng() {
		return signInLng;
	}

	public void setSignInLng(Double signInLng) {
		this.signInLng = signInLng;
	}

	public Byte getSignOutState() {
		return signOutState;
	}

	public void setSignOutState(Byte signOutState) {
		this.signOutState = signOutState;
	}

	public String getSignOutType() {
		return signOutType;
	}

	public void setSignOutType(String signOutType) {
		this.signOutType = signOutType;
	}

	public Byte getSignOutValid() {
		return signOutValid;
	}

	public void setSignOutValid(Byte signOutValid) {
		this.signOutValid = signOutValid;
	}

	public Date getSignOutTime() {
		return signOutTime;
	}

	public void setSignOutTime(Date signOutTime) {
		this.signOutTime = signOutTime;
	}

	public Integer getSignOutDiffTime() {
		return signOutDiffTime;
	}

	public void setSignOutDiffTime(Integer signOutDiffTime) {
		this.signOutDiffTime = signOutDiffTime;
	}

	public String getSignOutAddress() {
		return signOutAddress;
	}

	public void setSignOutAddress(String signOutAddress) {
		this.signOutAddress = signOutAddress;
	}

	public Double getSignOutLat() {
		return signOutLat;
	}

	public void setSignOutLat(Double signOutLat) {
		this.signOutLat = signOutLat;
	}

	public Double getSignOutLng() {
		return signOutLng;
	}

	public void setSignOutLng(Double signOutLng) {
		this.signOutLng = signOutLng;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}