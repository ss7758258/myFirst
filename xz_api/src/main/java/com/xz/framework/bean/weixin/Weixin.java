package com.xz.framework.bean.weixin;


import java.io.Serializable;

public class Weixin implements Serializable{

	private Long userId;//用户的ID
	private String openId;//微信openid
	private String commentUserAvatar;//微信登头像
	private String commentUserName;//微信昵称
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCommentUserAvatar() {
		return commentUserAvatar;
	}

	public void setCommentUserAvatar(String commentUserAvatar) {
		this.commentUserAvatar = commentUserAvatar;
	}

	public String getCommentUserName() {
		return commentUserName;
	}

	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}
}
