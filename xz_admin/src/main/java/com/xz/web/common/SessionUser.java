package com.xz.web.common;

import com.gface.web.bean.GfaceRoleInfo;
import com.gface.web.vo.v06.v062.UserInfoVo;
import com.gface.web.vo.v06.v064.OrgInfoVo;

import java.util.ArrayList;
import java.util.List;

public class SessionUser {

	private String userUid  = "";
	private String orgParentId ="";
	private String orgUId ="";
	private String roleUId ="";
	private String orgName ="";
	private String userName ="";
	private UserInfoVo userInfoVo ;
	private List<String> userUrlList;
	private List<GfaceRoleInfo> userRoleList;
	private  OrgInfoVo orgInfoVo;
	private String isLock ="false";
	
	public SessionUser(){
		
	}

	public SessionUser(String userUid, String orgParentId, String orgUId, String roleUId) {
		this.userUid = userUid;
		this.orgParentId = orgParentId;
		this.orgUId = orgUId;
		this.roleUId = roleUId;
	}

	public String getRoleUId() {
		return roleUId;
	}

	public void setRoleUId(String roleUId) {
		this.roleUId = roleUId;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getOrgParentId() {
		return orgParentId;
	}

	public void setOrgParentId(String orgParentId) {
		this.orgParentId = orgParentId;
	}

	public String getOrgUId() {
		return orgUId;
	}

	public void setOrgUId(String orgUId) {
		this.orgUId = orgUId;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public UserInfoVo getUserInfoVo() {
		return userInfoVo;
	}

	public void setUserInfoVo(UserInfoVo userInfoVo) {
		this.userInfoVo = userInfoVo;
	}

	public List<String> getUserUrlList() {
		if(userUrlList == null){
			userUrlList = new ArrayList<String>();
		}
		return userUrlList;
	}

	public void setUserUrlList(List<String> userUrlList) {
		this.userUrlList = userUrlList;
	}

	public OrgInfoVo getOrgInfoVo() {
		return orgInfoVo;
	}

	public void setOrgInfoVo(OrgInfoVo orgInfoVo) {
		this.orgInfoVo = orgInfoVo;
	}

	public List<GfaceRoleInfo> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<GfaceRoleInfo> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	
}
