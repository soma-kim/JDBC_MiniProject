package com.kh.model.vo;

public class Member {
	
	String userId;
	String userPwd;
	String productId;
	
	public Member() {
		super();
	}
	public Member(String userId, String userPwd, String productId) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.productId = productId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Override
	public String toString() {
		return "Member [userId=" + userId + ", userPwd=" + userPwd + ", productId=" + productId + "]";
	}
}
