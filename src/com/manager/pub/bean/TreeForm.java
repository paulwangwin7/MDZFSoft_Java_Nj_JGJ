package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class TreeForm {
	@Expose
	private long treeId;//部门id
	@Expose
	private String treeName;//部门名称
	@Expose
	private String treeDesc;//部门描述
	@Expose
	private String treeState;//部门状态
	@Expose
	private long createUser;//创建人id
	@Expose
	private String createTime;//创建时间
	@Expose
	private long parentTreeId;//父级treeId
	@Expose
	private String orderBy;//排序
	@Expose
	private String path; 

	public TreeForm(){}

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
	public String getTreeDesc() {
		return treeDesc;
	}
	public void setTreeDesc(String treeDesc) {
		this.treeDesc = treeDesc;
	}
	public String getTreeState() {
		return treeState;
	}
	public void setTreeState(String treeState) {
		this.treeState = treeState;
	}
	public long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(long createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public long getParentTreeId() {
		return parentTreeId;
	}
	public void setParentTreeId(long parentTreeId) {
		this.parentTreeId = parentTreeId;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
