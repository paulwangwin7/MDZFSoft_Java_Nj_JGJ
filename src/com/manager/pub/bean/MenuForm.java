package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class MenuForm {
	@Expose
	private long menuId;//菜单项id
	@Expose
	private String menuName;//菜单项名称
	@Expose
	private String menuSort;//菜单项排序
	@Expose
	private String menuState;//菜单项状态

	public MenuForm() {}

	public long getMenuId() {
		return menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuSort() {
		return menuSort;
	}
	public void setMenuSort(String menuSort) {
		this.menuSort = menuSort;
	}
	public String getMenuState() {
		return menuState;
	}
	public void setMenuState(String menuState) {
		this.menuState = menuState;
	}
}
