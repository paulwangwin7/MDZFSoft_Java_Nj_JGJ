package com.njmd.pojo;

/**
 * FrameUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameUser implements java.io.Serializable {

	// Fields

	private Long userId;
	private String loginName;
	private String loginPswd;
	private String userName;
	private String userCode;
	private String sex;
	private String userIdcard;
	private Long cardTypeid;
	private String cardCode;
	private Long treeId;
	private Long roleId;
	private String createTime;
	private String userState;

	// Constructors

	/** default constructor */
	public FrameUser() {
	}

	/** minimal constructor */
	public FrameUser(Long userId) {
		this.userId = userId;
	}

	/** full constructor */
	public FrameUser(Long userId, String loginName, String loginPswd,
			String userName, String userCode, String sex, String userIdcard,
			Long cardTypeid, String cardCode, Long treeId, Long roleId,
			String createTime, String userState) {
		this.userId = userId;
		this.loginName = loginName;
		this.loginPswd = loginPswd;
		this.userName = userName;
		this.userCode = userCode;
		this.sex = sex;
		this.userIdcard = userIdcard;
		this.cardTypeid = cardTypeid;
		this.cardCode = cardCode;
		this.treeId = treeId;
		this.roleId = roleId;
		this.createTime = createTime;
		this.userState = userState;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPswd() {
		return this.loginPswd;
	}

	public void setLoginPswd(String loginPswd) {
		this.loginPswd = loginPswd;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserIdcard() {
		return this.userIdcard;
	}

	public void setUserIdcard(String userIdcard) {
		this.userIdcard = userIdcard;
	}

	public Long getCardTypeid() {
		return this.cardTypeid;
	}

	public void setCardTypeid(Long cardTypeid) {
		this.cardTypeid = cardTypeid;
	}

	public String getCardCode() {
		return this.cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Long getTreeId() {
		return this.treeId;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserState() {
		return this.userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

}