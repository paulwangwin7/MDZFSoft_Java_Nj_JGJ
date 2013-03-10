package com.njmd.pojo;

/**
 * FrameUrl entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameUrl implements java.io.Serializable {

	// Fields

	private Long urlId;
	private String urlValue;
	private String urlName;
	private String urlDesc;
	private String urlState;
	private Long parentMenuId;
	private Long urlSort;
	private String urlTab;

	// Constructors

	/** default constructor */
	public FrameUrl() {
	}

	/** minimal constructor */
	public FrameUrl(Long urlId) {
		this.urlId = urlId;
	}

	/** full constructor */
	public FrameUrl(Long urlId, String urlValue, String urlName,
			String urlDesc, String urlState, Long parentMenuId, Long urlSort,
			String urlTab) {
		this.urlId = urlId;
		this.urlValue = urlValue;
		this.urlName = urlName;
		this.urlDesc = urlDesc;
		this.urlState = urlState;
		this.parentMenuId = parentMenuId;
		this.urlSort = urlSort;
		this.urlTab = urlTab;
	}

	// Property accessors

	public Long getUrlId() {
		return this.urlId;
	}

	public void setUrlId(Long urlId) {
		this.urlId = urlId;
	}

	public String getUrlValue() {
		return this.urlValue;
	}

	public void setUrlValue(String urlValue) {
		this.urlValue = urlValue;
	}

	public String getUrlName() {
		return this.urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getUrlDesc() {
		return this.urlDesc;
	}

	public void setUrlDesc(String urlDesc) {
		this.urlDesc = urlDesc;
	}

	public String getUrlState() {
		return this.urlState;
	}

	public void setUrlState(String urlState) {
		this.urlState = urlState;
	}

	public Long getParentMenuId() {
		return this.parentMenuId;
	}

	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public Long getUrlSort() {
		return this.urlSort;
	}

	public void setUrlSort(Long urlSort) {
		this.urlSort = urlSort;
	}

	public String getUrlTab() {
		return this.urlTab;
	}

	public void setUrlTab(String urlTab) {
		this.urlTab = urlTab;
	}

}