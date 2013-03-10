package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class LogForm {
	@Expose
	private long logId;//log_id
	@Expose
	private String createTime;//日志记录时间
	@Expose
	private long userId;//被记录人id
	@Expose
	private String userCode;//被记录工号
	@Expose
	private long treeId;//被记录人部门id
	@Expose
	private String treeName;//被记录人部门名称
	@Expose
	private long roleId;//被记录人角色id
	@Expose
	private String roleName;//被记录人角色名称
	@Expose
	private String logDesc;//日志描述
	@Expose
	private String ipAddr;//日志描述

	public LogForm(long userId, String userCode, long treeId, String treeName,
			long roleId, String roleName, String logDesc){
		this.userId = userId;
		this.userCode = userCode;
		this.treeId = treeId;
		this.treeName = treeName;
		this.roleId = roleId;
		this.roleName = roleName;
		this.logDesc = logDesc;
	}

	public LogForm(long userId, String userCode, long treeId, String treeName,
			long roleId, String roleName, String logDesc, String ipAddr){
		this.userId = userId;
		this.userCode = userCode;
		this.treeId = treeId;
		this.treeName = treeName;
		this.roleId = roleId;
		this.roleName = roleName;
		this.logDesc = logDesc;
		this.ipAddr = ipAddr;
	}

	public LogForm(){}

	public long getLogId() {
		return logId;
	}
	public void setLogId(long logId) {
		this.logId = logId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public long getTreeId() {
		return treeId;
	}
	public void setTreeId(long treeId) {
		this.treeId = treeId;
	}
	public String getTreeName() {
		return treeName;
	}
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getLogDesc() {
		return logDesc;
	}
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
}
