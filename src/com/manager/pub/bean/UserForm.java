package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class UserForm {
	@Expose
	private long userId;//用户id
	@Expose
	private String loginName;//登录账号
	@Expose
	private String loginPswd;//登录密码
	@Expose
	private String userName;//用户名
	@Expose
	private String userCode;//用户编号（警官编号）
	@Expose
	private String sex;//性别 M-男 W-女
	@Expose
	private String userIdCard;//身份证号
	@Expose
	private long card_typeId;//其他证件类别
	@Expose
	private String cardCode;//其他证件号
	@Expose
	private long treeId;//所属部门
	@Expose
	private long roleId;//所属角色
	@Expose
	private String createTime;//创建时间
	@Expose
	private String userState;//用户状态
	@Expose
	private String treeNameStr;//所属部门
	@Expose
	private String roleNameStr;//所属角色

	public UserForm(){}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPswd() {
		return loginPswd;
	}
	public void setLoginPswd(String loginPswd) {
		this.loginPswd = loginPswd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserIdCard() {
		return userIdCard;
	}
	public void setUserIdCard(String userIdCard) {
		this.userIdCard = userIdCard;
	}
	public long getCard_typeId() {
		return card_typeId;
	}
	public void setCard_typeId(long card_typeId) {
		this.card_typeId = card_typeId;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public long getTreeId() {
		return treeId;
	}
	public void setTreeId(long treeId) {
		this.treeId = treeId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getTreeNameStr() {
		return treeNameStr;
	}

	public void setTreeNameStr(String treeNameStr) {
		this.treeNameStr = treeNameStr;
	}

	public String getRoleNameStr() {
		return roleNameStr;
	}

	public void setRoleNameStr(String roleNameStr) {
		this.roleNameStr = roleNameStr;
	}
}
