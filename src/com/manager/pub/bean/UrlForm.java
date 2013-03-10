package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class UrlForm {
	@Expose
	private long urlId;//功能地址id
	@Expose
	private String urlValue;//功能地址url
	@Expose
	private String urlName;//功能地址名称
	@Expose
	private String urlDesc;//功能地址描述
	@Expose
	private String urlState;//功能地址状态
	@Expose
	private String parentMenuId;//所属菜单id
	@Expose
	private String urlSort;//功能地址排序
	@Expose
	private String urlTab;//功能地址标签

	public UrlForm() {}

	public long getUrlId() {
		return urlId;
	}
	public void setUrlId(long urlId) {
		this.urlId = urlId;
	}
	public String getUrlValue() {
		return urlValue;
	}
	public void setUrlValue(String urlValue) {
		this.urlValue = urlValue;
	}
	public String getUrlName() {
		return urlName;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	public String getUrlDesc() {
		return urlDesc;
	}
	public void setUrlDesc(String urlDesc) {
		this.urlDesc = urlDesc;
	}
	public String getUrlState() {
		return urlState;
	}
	public void setUrlState(String urlState) {
		this.urlState = urlState;
	}
	public String getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public String getUrlSort() {
		return urlSort;
	}
	public void setUrlSort(String urlSort) {
		this.urlSort = urlSort;
	}
	public String getUrlTab() {
		return urlTab;
	}
	public void setUrlTab(String urlTab) {
		this.urlTab = urlTab;
	}

}
