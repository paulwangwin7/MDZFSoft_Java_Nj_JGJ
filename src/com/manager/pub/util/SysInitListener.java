package com.manager.pub.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.manager.pub.bean.SystemConfig;

public class SysInitListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(SysInitListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		String str = sce.getServletContext().getInitParameter("log4jConfigLocation");
		//将xml文件路径保存
		try
		{
//			SystemConfigManager.setXstreamXMLPath(sce.getServletContext().getRealPath("")+ sce.getServletContext().getInitParameter("xStreamConfigLocation"));
//			System.out.println("sysConfig file path======"+SystemConfigManager.getXstreamXMLPath());
//			System.out.println(SystemConfig.getSystemConfig().getReadFilePath());
//			System.out.println(SystemConfig.getSystemConfig().getSaveFilePath());
//			System.out.println(SystemConfig.getSystemConfig().getFfmpegBegin());
//			System.out.println(SystemConfig.getSystemConfig().getFfmpegEnd());
//			System.out.println(SystemConfig.getSystemConfig().getSysLoginName());
//			System.out.println(SystemConfig.getSystemConfig().getSysLoginPswd());
			String aaa = sce.getServletContext().getRealPath("")+ sce.getServletContext().getInitParameter("propertiesConfigLocation");
			PropertiesUnit pu=new PropertiesUnit(aaa); 
			pu.list();
			SystemConfig sysConfig = new SystemConfig();
			sysConfig.setExpiredDay(pu.getValue("expiredDay"));
			sysConfig.setFileSystemDel(pu.getValue("fileSystemDel"));
			sysConfig.setFfmpegBegin(pu.getValue("ffmpegBegin"));
			sysConfig.setFfmpegEnd(pu.getValue("ffmpegEnd"));
			sysConfig.setFfmpegPath(pu.getValue("ffmpegPath"));
			sysConfig.setFfmpegSleep(pu.getValue("ffmpegSleep"));
			sysConfig.setFileRoot(pu.getValue("fileRoot"));
			sysConfig.setFileSavePath(pu.getValue("fileSavePath"));
			sysConfig.setFormatFactoryPath(pu.getValue("formatFactoryPath"));
			sysConfig.setFtpHost(pu.getValue("ftpHost"));
			sysConfig.setFtpPort(pu.getValue("ftpPort"));
			sysConfig.setFtpPswd(pu.getValue("ftpPswd"));
			sysConfig.setFtpUser(pu.getValue("ftpUser"));
			sysConfig.setLetter(pu.getValue("letter"));
			sysConfig.setListenUrl(pu.getValue("listenUrl"));
			sysConfig.setLoginIPFilter(pu.getValue("loginIPFilter"));
			sysConfig.setLoginIPList(null);
			sysConfig.setReadFilePath(pu.getValue("readFilePath"));
			sysConfig.setResetPswd(pu.getValue("resetPswd"));
			sysConfig.setSaveFilePath(pu.getValue("saveFilePath"));
			sysConfig.setSysLoginName(pu.getValue("sysLoginName"));
			sysConfig.setSysLoginPswd(pu.getValue("sysLoginPswd"));
			sysConfig.setMinfreeSpace(pu.getValue("minfreeSpace"));
			sysConfig.setSystemConfig(sysConfig);
		}
		catch(Exception ex)
		{
			logger.error("xml文件加载失败："+ex);
		}
	}
	
}
