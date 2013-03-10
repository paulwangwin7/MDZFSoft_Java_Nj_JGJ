package com.njmd.pojo;

/**
 * FrameUpload entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameUpload implements java.io.Serializable {

	// Fields

	private Long uploadId;
	private Long userId;
	private Long editId;
	private String uploadName;
	private String playPath;
	private String fileCreatetime;
	private String showPath;
	private String uploadTime;
	private String fileState;
	private Long tree2Id;
	private Long tree1Id;
	private String treeName;
	private String fileStats;
	private String fileRemark;
	private String ipAddr;
	private String realPath;
	private String flvPath;

	// Constructors

	/** default constructor */
	public FrameUpload() {
	}

	/** minimal constructor */
	public FrameUpload(Long uploadId) {
		this.uploadId = uploadId;
	}

	/** full constructor */
	public FrameUpload(Long uploadId, Long userId, Long editId,
			String uploadName, String playPath, String fileCreatetime,
			String showPath, String uploadTime, String fileState, Long tree2Id,
			Long tree1Id, String treeName, String fileStats, String fileRemark,
			String ipAddr, String realPath, String flvPath) {
		this.uploadId = uploadId;
		this.userId = userId;
		this.editId = editId;
		this.uploadName = uploadName;
		this.playPath = playPath;
		this.fileCreatetime = fileCreatetime;
		this.showPath = showPath;
		this.uploadTime = uploadTime;
		this.fileState = fileState;
		this.tree2Id = tree2Id;
		this.tree1Id = tree1Id;
		this.treeName = treeName;
		this.fileStats = fileStats;
		this.fileRemark = fileRemark;
		this.ipAddr = ipAddr;
		this.realPath = realPath;
		this.flvPath = flvPath;
	}

	// Property accessors

	public Long getUploadId() {
		return this.uploadId;
	}

	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getEditId() {
		return this.editId;
	}

	public void setEditId(Long editId) {
		this.editId = editId;
	}

	public String getUploadName() {
		return this.uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public String getPlayPath() {
		return this.playPath;
	}

	public void setPlayPath(String playPath) {
		this.playPath = playPath;
	}

	public String getFileCreatetime() {
		return this.fileCreatetime;
	}

	public void setFileCreatetime(String fileCreatetime) {
		this.fileCreatetime = fileCreatetime;
	}

	public String getShowPath() {
		return this.showPath;
	}

	public void setShowPath(String showPath) {
		this.showPath = showPath;
	}

	public String getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getFileState() {
		return this.fileState;
	}

	public void setFileState(String fileState) {
		this.fileState = fileState;
	}

	public Long getTree2Id() {
		return this.tree2Id;
	}

	public void setTree2Id(Long tree2Id) {
		this.tree2Id = tree2Id;
	}

	public Long getTree1Id() {
		return this.tree1Id;
	}

	public void setTree1Id(Long tree1Id) {
		this.tree1Id = tree1Id;
	}

	public String getTreeName() {
		return this.treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public String getFileStats() {
		return this.fileStats;
	}

	public void setFileStats(String fileStats) {
		this.fileStats = fileStats;
	}

	public String getFileRemark() {
		return this.fileRemark;
	}

	public void setFileRemark(String fileRemark) {
		this.fileRemark = fileRemark;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getRealPath() {
		return this.realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getFlvPath() {
		return this.flvPath;
	}

	public void setFlvPath(String flvPath) {
		this.flvPath = flvPath;
	}

}