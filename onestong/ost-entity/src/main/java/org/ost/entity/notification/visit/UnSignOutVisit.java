package org.ost.entity.notification.visit;

/**
 * 未签退外访
 * 
 * @author xnq
 *
 */
public class UnSignOutVisit {

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 总共多少条记录
	 */
	private Integer total;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "UnSignOutVisit [userId=" + userId + ", userName=" + userName + ", total=" + total + ", clientId="
				+ clientId + "]";
	}
	
	

}
