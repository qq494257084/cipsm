package com.tiens.cipsm.entity;


import java.io.Serializable;

/**
 * 园区关联表
 * 
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019-05-11 14:02:21
 */
public class CipsmUserParkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * UUID
	 */
	private String userParkId;
	/**
	 * 用户ID
	 */
	private String userParkUserId;
	/**
	 * 园区ID
	 */
	private String userParkParkId;

	public String getUserParkId() {
		return userParkId;
	}

	public void setUserParkId(String userParkId) {
		this.userParkId = userParkId;
	}

	public String getUserParkUserId() {
		return userParkUserId;
	}

	public void setUserParkUserId(String userParkUserId) {
		this.userParkUserId = userParkUserId;
	}

	public String getUserParkParkId() {
		return userParkParkId;
	}

	public void setUserParkParkId(String userParkParkId) {
		this.userParkParkId = userParkParkId;
	}
}
