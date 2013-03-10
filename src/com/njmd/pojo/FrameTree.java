package com.njmd.pojo;

/**
 * FrameTree entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameTree implements java.io.Serializable {

	// Fields

	private Long treeId;
	private String treeName;
	private String treeDesc;
	private String treeState;
	private Long createUser;
	private String createTime;
	private Long parentTreeId;
	private Long orderBy;

	// Constructors

	/** default constructor */
	public FrameTree() {
	}

	/** minimal constructor */
	public FrameTree(Long treeId) {
		this.treeId = treeId;
	}

	/** full constructor */
	public FrameTree(Long treeId, String treeName, String treeDesc,
			String treeState, Long createUser, String createTime,
			Long parentTreeId, Long orderBy) {
		this.treeId = treeId;
		this.treeName = treeName;
		this.treeDesc = treeDesc;
		this.treeState = treeState;
		this.createUser = createUser;
		this.createTime = createTime;
		this.parentTreeId = parentTreeId;
		this.orderBy = orderBy;
	}

	// Property accessors

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

	public String getTreeDesc() {
		return this.treeDesc;
	}

	public void setTreeDesc(String treeDesc) {
		this.treeDesc = treeDesc;
	}

	public String getTreeState() {
		return this.treeState;
	}

	public void setTreeState(String treeState) {
		this.treeState = treeState;
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

	public Long getParentTreeId() {
		return this.parentTreeId;
	}

	public void setParentTreeId(Long parentTreeId) {
		this.parentTreeId = parentTreeId;
	}

	public Long getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Long orderBy) {
		this.orderBy = orderBy;
	}

}