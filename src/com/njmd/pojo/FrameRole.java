package com.njmd.pojo;

/**
 * FrameRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameRole implements java.io.Serializable {

	// Fields

	private Long roleId;
	private String roleName;
	private String roleDesc;
	private String roleState;
	private Long createUser;
	private String createTime;
	private Long treeId;
	private String urlIdList;

	// Constructors

	/** default constructor */
	public FrameRole() {
	}

	/** minimal constructor */
	public FrameRole(Long roleId) {
		this.roleId = roleId;
	}

	/** full constructor */
	public FrameRole(Long roleId, String roleName, String roleDesc,
			String roleState, Long createUser, String createTime, Long treeId,
			String urlIdList) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.roleState = roleState;
		this.createUser = createUser;
		this.createTime = createTime;
		this.treeId = treeId;
		this.urlIdList = urlIdList;
	}

	// Property accessors

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

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleState() {
		return this.roleState;
	}

	public void setRoleState(String roleState) {
		this.roleState = roleState;
	}

	public Long getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getTreeId() {
		return this.treeId;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}

	public String getUrlIdList() {
		return this.urlIdList;
	}

	public void setUrlIdList(String urlIdList) {
		this.urlIdList = urlIdList;
	}

}