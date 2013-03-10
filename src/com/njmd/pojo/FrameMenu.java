package com.njmd.pojo;

/**
 * FrameMenu entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameMenu implements java.io.Serializable {

	// Fields

	private Long menuId;
	private String menuName;
	private Long menuSort;
	private String menuState;

	// Constructors

	/** default constructor */
	public FrameMenu() {
	}

	/** minimal constructor */
	public FrameMenu(Long menuId) {
		this.menuId = menuId;
	}

	/** full constructor */
	public FrameMenu(Long menuId, String menuName, Long menuSort,
			String menuState) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuSort = menuSort;
		this.menuState = menuState;
	}

	// Property accessors

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Long getMenuSort() {
		return this.menuSort;
	}

	public void setMenuSort(Long menuSort) {
		this.menuSort = menuSort;
	}

	public String getMenuState() {
		return this.menuState;
	}

	public void setMenuState(String menuState) {
		this.menuState = menuState;
	}

}