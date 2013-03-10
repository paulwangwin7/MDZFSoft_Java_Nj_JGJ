package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class AdminInfoForm {
	@Expose
	private String loginName;//登录名
	@Expose
	private String loginPswd;//登录密码
	@Expose
	private String loginIP;//登录使用的IP地址
	@Expose
	private String loginTime;//登录时间

	public AdminInfoForm(){}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPswd() {
		return loginPswd;
	}

	public void setLoginPswd(String loginPswd) {
		this.loginPswd = loginPswd;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
}
