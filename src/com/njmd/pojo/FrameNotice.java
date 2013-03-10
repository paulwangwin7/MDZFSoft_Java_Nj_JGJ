package com.njmd.pojo;

/**
 * FrameNotice entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameNotice implements java.io.Serializable {

	// Fields

	private Long noticeId;
	private String noticeTitle;
	private String noticeContent;
	private String noticeType;
	private String createTime;
	private String noticeBegin;
	private String noticeEnd;
	private Long userId;
	private String treeIdList;

	// Constructors

	/** default constructor */
	public FrameNotice() {
	}

	/** minimal constructor */
	public FrameNotice(Long noticeId) {
		this.noticeId = noticeId;
	}

	/** full constructor */
	public FrameNotice(Long noticeId, String noticeTitle, String noticeContent,
			String noticeType, String createTime, String noticeBegin,
			String noticeEnd, Long userId, String treeIdList) {
		this.noticeId = noticeId;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeType = noticeType;
		this.createTime = createTime;
		this.noticeBegin = noticeBegin;
		this.noticeEnd = noticeEnd;
		this.userId = userId;
		this.treeIdList = treeIdList;
	}

	// Property accessors

	public Long getNoticeId() {
		return this.noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeTitle() {
		return this.noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return this.noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeType() {
		return this.noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getNoticeBegin() {
		return this.noticeBegin;
	}

	public void setNoticeBegin(String noticeBegin) {
		this.noticeBegin = noticeBegin;
	}

	public String getNoticeEnd() {
		return this.noticeEnd;
	}

	public void setNoticeEnd(String noticeEnd) {
		this.noticeEnd = noticeEnd;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTreeIdList() {
		return this.treeIdList;
	}

	public void setTreeIdList(String treeIdList) {
		this.treeIdList = treeIdList;
	}

}