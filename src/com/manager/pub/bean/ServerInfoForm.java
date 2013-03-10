package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class ServerInfoForm {
	@Expose
	private long serverinfoId;//服务器监控id
	@Expose
	private int ratioCPU;//CPU占用率
	@Expose
	private int ratioMEMORY;//内存占用率
	@Expose
	private String useMEMORY;//内存使用（单位：kb）
	@Expose
	private int ratioHARDDISK;//硬盘占用率
	@Expose
	private String useHARDDISK;//硬盘使用（单位：gb)
	@Expose
	private String letter;//硬盘盘符
	@Expose
	private String createTime;//记录时间
	@Expose
	private String saveIp;//记录IP

	public ServerInfoForm(){}

	public long getServerinfoId() {
		return serverinfoId;
	}
	public void setServerinfoId(long serverinfoId) {
		this.serverinfoId = serverinfoId;
	}
	public int getRatioCPU() {
		return ratioCPU;
	}
	public void setRatioCPU(int ratioCPU) {
		this.ratioCPU = ratioCPU;
	}
	public int getRatioMEMORY() {
		return ratioMEMORY;
	}
	public void setRatioMEMORY(int ratioMEMORY) {
		this.ratioMEMORY = ratioMEMORY;
	}
	public String getUseMEMORY() {
		return useMEMORY;
	}
	public void setUseMEMORY(String useMEMORY) {
		this.useMEMORY = useMEMORY;
	}
	public int getRatioHARDDISK() {
		return ratioHARDDISK;
	}
	public void setRatioHARDDISK(int ratioHARDDISK) {
		this.ratioHARDDISK = ratioHARDDISK;
	}
	public String getUseHARDDISK() {
		return useHARDDISK;
	}
	public void setUseHARDDISK(String useHARDDISK) {
		this.useHARDDISK = useHARDDISK;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getCreateTime() {
		return createTime;
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
