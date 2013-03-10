package com.njmd.pojo;

/**
 * FrameServerinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FrameServerinfo implements java.io.Serializable {

	// Fields

	private Long serverinfoId;
	private Long ratioCpu;
	private Long ratioMemory;
	private String useMemory;
	private Long ratioHarddisk;
	private String useHarddisk;
	private String letter;
	private String createTime;
	private String saveIp;

	// Constructors

	/** default constructor */
	public FrameServerinfo() {
	}

	/** minimal constructor */
	public FrameServerinfo(Long serverinfoId) {
		this.serverinfoId = serverinfoId;
	}

	/** full constructor */
	public FrameServerinfo(Long serverinfoId, Long ratioCpu, Long ratioMemory,
			String useMemory, Long ratioHarddisk, String useHarddisk,
			String letter, String createTime, String saveIp) {
		this.serverinfoId = serverinfoId;
		this.ratioCpu = ratioCpu;
		this.ratioMemory = ratioMemory;
		this.useMemory = useMemory;
		this.ratioHarddisk = ratioHarddisk;
		this.useHarddisk = useHarddisk;
		this.letter = letter;
		this.createTime = createTime;
		this.saveIp = saveIp;
	}

	// Property accessors

	public Long getServerinfoId() {
		return this.serverinfoId;
	}

	public void setServerinfoId(Long serverinfoId) {
		this.serverinfoId = serverinfoId;
	}

	public Long getRatioCpu() {
		return this.ratioCpu;
	}

	public void setRatioCpu(Long ratioCpu) {
		this.ratioCpu = ratioCpu;
	}

	public Long getRatioMemory() {
		return this.ratioMemory;
	}

	public void setRatioMemory(Long ratioMemory) {
		this.ratioMemory = ratioMemory;
	}

	public String getUseMemory() {
		return this.useMemory;
	}

	public void setUseMemory(String useMemory) {
		this.useMemory = useMemory;
	}

	public Long getRatioHarddisk() {
		return this.ratioHarddisk;
	}

	public void setRatioHarddisk(Long ratioHarddisk) {
		this.ratioHarddisk = ratioHarddisk;
	}

	public String getUseHarddisk() {
		return this.useHarddisk;
	}

	public void setUseHarddisk(String useHarddisk) {
		this.useHarddisk = useHarddisk;
	}

	public String getLetter() {
		return this.letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSaveIp() {
		return saveIp;
	}

	public void setSaveIp(String saveIp) {
		this.saveIp = saveIp;
	}

}