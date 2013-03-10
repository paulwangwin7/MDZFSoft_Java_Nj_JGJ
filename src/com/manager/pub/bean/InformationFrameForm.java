package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class InformationFrameForm {
	@Expose
	private String message;//信息提示内容
	@Expose
	private String gotoUrl;//相关页面地址
	@Expose
	private String frameMsgCode;//0-操作成功；1-操作失败
	@Expose
	private String tabName;//tab页面键值对
	@Expose
	private String tabTitle;//tab标题内容

	public InformationFrameForm(){}

	public InformationFrameForm(String message,String gotoUrl, String frameMsgCode, String tabName, String tabTitle) {
		this.message = message;
		this.gotoUrl = gotoUrl;
		this.frameMsgCode = frameMsgCode;
		this.tabName = tabName;
		this.tabTitle = tabTitle;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getGotoUrl() {
		return gotoUrl;
	}
	public void setGotoUrl(String gotoUrl) {
		this.gotoUrl = gotoUrl;
	}
	public String getFrameMsgCode() {
		return frameMsgCode;
	}
	public void setFrameMsgCode(String frameMsgCode) {
		this.frameMsgCode = frameMsgCode;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getTabTitle() {
		return tabTitle;
	}
	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}
}
