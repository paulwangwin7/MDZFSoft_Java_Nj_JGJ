package com.manager.pub.bean;

import java.util.List;

import com.manager.pub.util.SystemConfigManager;

public class SystemConfig {
	private String readFilePath;//转avi成flv avi读取的盘符路径
	private String saveFilePath;//转avi成flv flv生成的盘符路径
	private String ffmpegBegin;	//转avi成flv 开始时间
	private String ffmpegEnd;	//转avi成flv 结束时间
	private String ffmpegSleep;	//转avi成flv 休眠时间

	private String sysLoginName;//系统管理员登录帐号
	private String sysLoginPswd;//系统管理员登录密码
	private String loginIPFilter;//是否要进行ip验证，Y-是 其他否
	private List<String> loginIPList;//可以登录的ip

	private String fileRoot;	//资源文件存放根目录
	private String ffmpegPath;	//截图工具路径
	private String formatFactoryPath;//格式工厂路径
	private String fileSavePath;//保存contextPath路径
	private String letter;		//监控服务器硬盘的盘符
	private String minfreeSpace;		//硬盘最小空闲阀值

	private String expiredDay;	//文件有效期 7天
	private String fileSystemDel;//是否系统删除过期文件 Y-是 其他否
	private String resetPswd;	//重置默认密码

	private String ftpHost;		//ftp服务器请求ip地址
	private String ftpPort;		//ftp服务器端口
	private String ftpUser;		//ftp服务器登录用户名
	private String ftpPswd;		//ftp服务器登录密码

	private String listenUrl;	//监听地址

	public static SystemConfig systemConfig = null;

	/**
	 * 获取系统配置 单例模式
	 */
	public static SystemConfig getSystemConfig() {
		if(systemConfig==null)
		{
			try
			{
				setSystemConfig(SystemConfigManager.getSystemConfig());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return systemConfig;
	}

	/**
	 * 重新加载系统配置 慎用
	 */
	public static void reloadSystemConfig() {
		try
		{
			setSystemConfig(SystemConfigManager.getSystemConfig());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void setSystemConfig(SystemConfig systemConfig) {
		SystemConfig.systemConfig = systemConfig;
	}

	public SystemConfig(){}

	public String getReadFilePath() {
		return readFilePath;
	}

	public void setReadFilePath(String readFilePath) {
		this.readFilePath = readFilePath;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

	public String getFfmpegBegin() {
		return ffmpegBegin;
	}

	public void setFfmpegBegin(String ffmpegBegin) {
		this.ffmpegBegin = ffmpegBegin;
	}

	public String getFfmpegEnd() {
		return ffmpegEnd;
	}

	public void setFfmpegEnd(String ffmpegEnd) {
		this.ffmpegEnd = ffmpegEnd;
	}

	public String getSysLoginName() {
		return sysLoginName;
	}

	public void setSysLoginName(String sysLoginName) {
		this.sysLoginName = sysLoginName;
	}

	public String getSysLoginPswd() {
		return sysLoginPswd;
	}

	public void setSysLoginPswd(String sysLoginPswd) {
		this.sysLoginPswd = sysLoginPswd;
	}

	public String getFileRoot() {
		return fileRoot;
	}

	public void setFileRoot(String fileRoot) {
		this.fileRoot = fileRoot;
	}

	public String getFfmpegPath() {
		return ffmpegPath;
	}

	public void setFfmpegPath(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}

	public String getFormatFactoryPath() {
		return formatFactoryPath;
	}

	public void setFormatFactoryPath(String formatFactoryPath) {
		this.formatFactoryPath = formatFactoryPath;
	}

	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getMinfreeSpace() {
		return minfreeSpace;
	}

	public void setMinfreeSpace(String minfreeSpace) {
		this.minfreeSpace = minfreeSpace;
	}

	public String getLoginIPFilter() {
		return loginIPFilter;
	}

	public void setLoginIPFilter(String loginIPFilter) {
		this.loginIPFilter = loginIPFilter;
	}

	public List<String> getLoginIPList() {
		return loginIPList;
	}

	public void setLoginIPList(List<String> loginIPList) {
		this.loginIPList = loginIPList;
	}

	public String getExpiredDay() {
		return expiredDay;
	}

	public void setExpiredDay(String expiredDay) {
		this.expiredDay = expiredDay;
	}

	public String getResetPswd() {
		return resetPswd;
	}

	public void setResetPswd(String resetPswd) {
		this.resetPswd = resetPswd;
	}

	public String getFfmpegSleep() {
		return ffmpegSleep;
	}

	public void setFfmpegSleep(String ffmpegSleep) {
		this.ffmpegSleep = ffmpegSleep;
	}

	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPswd() {
		return ftpPswd;
	}

	public void setFtpPswd(String ftpPswd) {
		this.ftpPswd = ftpPswd;
	}

	public String getListenUrl() {
		return listenUrl;
	}

	public void setListenUrl(String listenUrl) {
		this.listenUrl = listenUrl;
	}

	public String getFileSystemDel() {
		return fileSystemDel;
	}

	public void setFileSystemDel(String fileSystemDel) {
		this.fileSystemDel = fileSystemDel;
	}

	
}
