package com.manager.pub.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Result {
	@Expose
	private boolean success = true;
	@Expose
	private int retCode = 0;
	@Expose
	private String msg;
	@Expose
	private Object retObj;

	public Result() {

	}

	public Result(Object object) {
		this.retObj = object;
	}

	public Result(int code, Object object) {
		this.retCode = code;
		this.retObj = object;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getRetObj() {
		return retObj;
	}

	public void setRetObj(Object retObj) {
		this.retObj = retObj;
	}

	public static void main(String[] args)
	{
		Result rst = new Result();
		List<UserForm> list = new ArrayList<UserForm>();
		UserForm userForm = new UserForm();
		userForm.setLoginName("张杰");
		userForm.setUserName("zhangjie");
		list.add(userForm);
		userForm = new UserForm();
		userForm.setLoginName("许梅");
		userForm.setUserName("xumei");
		list.add(userForm);
		rst.setRetObj(list);
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();
		String sRst = gson.toJson(rst);
		System.out.println(sRst);
	}
}
