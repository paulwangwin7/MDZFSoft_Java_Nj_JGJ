package com.njmd.pojo;

/**
 * FrameRemarkId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameRemarkId implements java.io.Serializable {

	// Fields

	private Long remarkId;
	private String remarkTable;
	private String remarkContent;
	private String createTime;

	// Constructors

	/** default constructor */
	public FrameRemarkId() {
	}

	/** full constructor */
	public FrameRemarkId(Long remarkId, String remarkTable,
			String remarkContent, String createTime) {
		this.remarkId = remarkId;
		this.remarkTable = remarkTable;
		this.remarkContent = remarkContent;
		this.createTime = createTime;
	}

	// Property accessors

	public Long getRemarkId() {
		return this.remarkId;
	}

	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
	}

	public String getRemarkTable() {
		return this.remarkTable;
	}

	public void setRemarkTable(String remarkTable) {
		this.remarkTable = remarkTable;
	}

	public String getRemarkContent() {
		return this.remarkContent;
	}

	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FrameRemarkId))
			return false;
		FrameRemarkId castOther = (FrameRemarkId) other;

		return ((this.getRemarkId() == castOther.getRemarkId()) || (this
				.getRemarkId() != null
				&& castOther.getRemarkId() != null && this.getRemarkId()
				.equals(castOther.getRemarkId())))
				&& ((this.getRemarkTable() == castOther.getRemarkTable()) || (this
						.getRemarkTable() != null
						&& castOther.getRemarkTable() != null && this
						.getRemarkTable().equals(castOther.getRemarkTable())))
				&& ((this.getRemarkContent() == castOther.getRemarkContent()) || (this
						.getRemarkContent() != null
						&& castOther.getRemarkContent() != null && this
						.getRemarkContent()
						.equals(castOther.getRemarkContent())))
				&& ((this.getCreateTime() == castOther.getCreateTime()) || (this
						.getCreateTime() != null
						&& castOther.getCreateTime() != null && this
						.getCreateTime().equals(castOther.getCreateTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRemarkId() == null ? 0 : this.getRemarkId().hashCode());
		result = 37
				* result
				+ (getRemarkTable() == null ? 0 : this.getRemarkTable()
						.hashCode());
		result = 37
				* result
				+ (getRemarkContent() == null ? 0 : this.getRemarkContent()
						.hashCode());
		result = 37
				* result
				+ (getCreateTime() == null ? 0 : this.getCreateTime()
						.hashCode());
		return result;
	}

}