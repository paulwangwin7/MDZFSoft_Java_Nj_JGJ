package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class JspForm {
	@Expose
	private String title;//信息提示标题
	@Expose
	private String message;//信息提示内容
	@Expose
	private boolean ifBack;//是否有后退
	@Expose
	private boolean ifGoto;//是否有相关页面
	@Expose
	private String gotoUrl;//相关页面地址
	@Expose
	private boolean ifFrame = false;//是否是iframe界面
	@Expose
	private String frameMsgCode;//0-操作成功；1-操作失败
	@Expose
	private String tabName;//tab页面键值对
	@Expose
	private String tabTitle;//tab标题内容

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

	public String getFrameMsgCode() {
		return frameMsgCode;
	}

	public void setFrameMsgCode(String frameMsgCode) {
		this.frameMsgCode = frameMsgCode;
	}

	public JspForm(){}

	public JspForm(String title, String message,
			boolean ifBack, boolean ifGoto, String gotoUrl, boolean ifFrame, String frameMsgCode, String tabName, String tabTitle) {
		this.title = title;
		this.message = message;
		this.ifBack = ifBack;
		this.ifGoto = ifGoto;
		this.gotoUrl = gotoUrl;
		this.ifFrame = ifFrame;
		this.frameMsgCode = frameMsgCode;
		this.tabName = tabName;
		this.tabTitle = tabTitle;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean getIfBack() {
		return ifBack;
	}
	public void setIfBack(boolean ifBack) {
		this.ifBack = ifBack;
	}
	public boolean getIfGoto() {
		return ifGoto;
	}
	public void setIfGoto(boolean ifGoto) {
		this.ifGoto = ifGoto;
	}
	public String getGotoUrl() {
		return gotoUrl;
	}
	public void setGotoUrl(String gotoUrl) {
		this.gotoUrl = gotoUrl;
	}

	public boolean getIfFrame() {
		return ifFrame;
	}

	public void setIfFrame(boolean ifFrame) {
		this.ifFrame = ifFrame;
	}
}
