package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class RemarkForm {
	@Expose
	private long remarkId;
	@Expose
	private String remarkTable;
	@Expose
	private String remarkContent;
	@Expose
	private String createTime;

	public RemarkForm(){}

	public long getRemarkId() {
		return remarkId;
	}
	public void setRemarkId(long remarkId) {
		this.remarkId = remarkId;
	}
	public String getRemarkTable() {
		return remarkTable;
	}
	public void setRemarkTable(String remarkTable) {
		this.remarkTable = remarkTable;
	}
	public String getRemarkContent() {
		return remarkContent;
	}
	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
