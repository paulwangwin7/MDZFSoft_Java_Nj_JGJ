package com.njmd.pojo;

/**
 * FrameRemark entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameRemark implements java.io.Serializable {

	// Fields

	private FrameRemarkId id;

	// Constructors

	/** default constructor */
	public FrameRemark() {
	}

	/** full constructor */
	public FrameRemark(FrameRemarkId id) {
		this.id = id;
	}

	// Property accessors

	public FrameRemarkId getId() {
		return this.id;
	}

	public void setId(FrameRemarkId id) {
		this.id = id;
	}

}