package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class NoticeForm {
	@Expose
	private long noticeId;//公告id
	@Expose
	private String noticeTitle;//公告标题
	@Expose
	private String noticeContent;//公告内容
	@Expose
	private String noticeType;//公告类型 1-首页公告；2-消息公告
	@Expose
	private String createTime;//公告创建时间
	@Expose
	private String noticeBegin;//公告开始时间 格式xxxx年xx月xx日xx时xx秒 24小时制
	@Expose
	private String noticeEnd;//公告结束时间
	@Expose
	private long userId;//公告发布人id
	@Expose
	private String treeIdList;//公告部门
	@Expose
	private String editer;//公告编辑人
	@Expose
	private String treeIdListStr;//公告部门

	public NoticeForm(){}

	public long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(long noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getNoticeBegin() {
		return noticeBegin;
	}
	public void setNoticeBegin(String noticeBegin) {
		this.noticeBegin = noticeBegin;
	}
	public String getNoticeEnd() {
		return noticeEnd;
	}
	public void setNoticeEnd(String noticeEnd) {
		this.noticeEnd = noticeEnd;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getTreeIdList() {
		return treeIdList;
	}
	public void setTreeIdList(String treeIdList) {
		this.treeIdList = treeIdList;
	}

	public String getEditer() {
		return editer;
	}

	public void setEditer(String editer) {
		this.editer = editer;
	}

	public String getTreeIdListStr() {
		return treeIdListStr;
	}

	public void setTreeIdListStr(String treeIdListStr) {
		this.treeIdListStr = treeIdListStr;
	}
}
