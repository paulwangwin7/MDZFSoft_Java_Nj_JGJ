package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class RoleForm {
	@Expose
	private long roleId;//角色id
	@Expose
	private String roleName;//角色名称
	@Expose
	private String roleDesc;//角色描述
	@Expose
	private String roleState;//角色状态
	@Expose
	private String createUser;//创建人id
	@Expose
	private String createTime;//创建时间
	@Expose
	private String treeId;//所属部门
	@Expose
	private String urlIdList;//权限对应的功能url项

	public RoleForm(){}

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
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getRoleState() {
		return roleState;
	}
	public void setRoleState(String roleState) {
		this.roleState = roleState;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public String getUrlIdList() {
		return urlIdList;
	}
	public void setUrlIdList(String urlIdList) {
		this.urlIdList = urlIdList;
	}
}
