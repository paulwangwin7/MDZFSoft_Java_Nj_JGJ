package com.njmd.pojo;

/**
 * FrameLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameLog implements java.io.Serializable {

	// Fields

	private Long logId;
	private String createTime;
	private Long userId;
	private String userCode;
	private Long treeId;
	private String treeName;
	private Long roleId;
	private String roleName;
	private String logDesc;
	private String ipAdd;

	// Constructors

	/** default constructor */
	public FrameLog() {
	}

	/** minimal constructor */
	public FrameLog(Long logId) {
		this.logId = logId;
	}

	/** full constructor */
	public FrameLog(Long logId, String createTime, Long userId,
			String userCode, Long treeId, String treeName, Long roleId,
			String roleName, String logDesc, String ipAdd) {
		this.logId = logId;
		this.createTime = createTime;
		this.userId = userId;
		this.userCode = userCode;
		this.treeId = treeId;
		this.treeName = treeName;
		this.roleId = roleId;
		this.roleName = roleName;
		this.logDesc = logDesc;
		this.ipAdd = ipAdd;
	}

	// Property accessors

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Long getTreeId() {
		return this.treeId;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}

	public String getTreeName() {
		return this.treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getLogDesc() {
		return this.logDesc;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

	public String getIpAdd() {
		return this.ipAdd;
	}

	public void setIpAdd(String ipAdd) {
		this.ipAdd = ipAdd;
	}

}