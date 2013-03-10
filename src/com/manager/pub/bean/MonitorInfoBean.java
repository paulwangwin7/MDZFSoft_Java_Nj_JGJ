package com.manager.pub.bean;

import com.google.gson.annotations.Expose;

public class MonitorInfoBean {
	/** 可使用内存. */  
	@Expose
    private long totalMemory;   
    /** 剩余内存. */  
	@Expose
    private long freeMemory;   
    /** 最大可使用内存. */  
	@Expose
    private long maxMemory;   
    /** 操作系统. */  
	@Expose
    private String osName;   
    /** 总的物理内存. */  
	@Expose
    private long totalMemorySize;   
    /** 剩余的物理内存. */  
	@Expose
    private long freePhysicalMemorySize;   
    /** 已使用的物理内存. */  
	@Expose
    private long usedMemory;   
    /** 线程总数. */  
	@Expose
    private int totalThread;   
    /** cpu使用率. */
	@Expose
    private double cpuRatio;
    /** 硬盘总大小. */
	@Expose
    private String harddiskTotal;
    /** 硬盘剩余大小 */
	@Expose
    private String harddiskFree;

	public String getHarddiskTotal() {
		return harddiskTotal;
	}
	public void setHarddiskTotal(String harddiskTotal) {
		this.harddiskTotal = harddiskTotal;
	}
	public String getHarddiskFree() {
		return harddiskFree;
	}
	public void setHarddiskFree(String harddiskFree) {
		this.harddiskFree = harddiskFree;
	}
	public double getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}
	public long getFreeMemory() {
		return freeMemory;
	}
	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}
	public long getMaxMemory() {
		return maxMemory;
	}
	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public long getTotalMemorySize() {
		return totalMemorySize;
	}
	public void setTotalMemorySize(long totalMemorySize) {
		this.totalMemorySize = totalMemorySize;
	}
	public long getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}
	public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
		this.freePhysicalMemorySize = freePhysicalMemorySize;
	}
	public long getUsedMemory() {
		return usedMemory;
	}
	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}
	public int getTotalThread() {
		return totalThread;
	}
	public void setTotalThread(int totalThread) {
		this.totalThread = totalThread;
	}
	public double getCpuRatio() {
		return cpuRatio;
	}
	public void setCpuRatio(double cpuRatio) {
		this.cpuRatio = cpuRatio;
	} 

    
}
