package com.manager.pub.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.manager.pub.bean.SystemConfig;
import com.thoughtworks.xstream.XStream;

public class SystemConfigManager {
	private static String xstreamXMLPath;

	public static String getXstreamXMLPath() {
		return xstreamXMLPath;
	}

	public static void setXstreamXMLPath(String xstreamXMLPath) {
		SystemConfigManager.xstreamXMLPath = xstreamXMLPath;
	}

	public static SystemConfig getSystemConfig() throws IOException {
		BufferedReader br = null;
		FileReader fr = null;
		SystemConfig systemConfig = null;
		try
		{
			fr = new FileReader(xstreamXMLPath);
			br = new BufferedReader(fr);
			StringBuffer strbuffer = new StringBuffer();
			String stt;
			while ((stt = br.readLine()) != null) {
				strbuffer.append(stt);
			}
			XStream xstream = new XStream();
			systemConfig = (SystemConfig) xstream.fromXML(strbuffer.toString());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			fr.close();
			br.close();
		}
		return systemConfig;
	}

	public static void main(String[] args) {
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setReadFilePath("E:/");
		systemConfig.setSaveFilePath("F:/");
		systemConfig.setFfmpegBegin("22");
		systemConfig.setFfmpegEnd("05");
		systemConfig.setFfmpegSleep("60000");

		systemConfig.setLoginIPFilter("Y");
		List<String> ipList = new ArrayList<String>();
		ipList.add("127.0.0.1");
		ipList.add("192.168.0.102");
		ipList.add("192.168.0.110");
		systemConfig.setLoginIPList(ipList);

		systemConfig.setSysLoginName("admin");
		systemConfig.setSysLoginPswd("121212");

		systemConfig.setFileRoot("D:\\TrafficSysFiles\\");
		systemConfig.setFfmpegPath("D:\\ffmpeg\\Libs\\ffmpeg");
		systemConfig.setFormatFactoryPath("D:\\FreeTime\\FormatFactory\\FFModules\\ffmpeg");
		systemConfig.setFileSavePath("http://192.168.16.204");
		systemConfig.setLetter("D");

		systemConfig.setExpiredDay("8");
		systemConfig.setResetPswd("888888");

		systemConfig.setFtpHost("192.168.16.204");
		systemConfig.setFtpPort("21");
		systemConfig.setFtpUser("ftpdemo");
		systemConfig.setFtpPswd("ftpdemo");

		XStream xstream = new XStream();
		String result = xstream.toXML(systemConfig);
		try {
			FileWriter file = new FileWriter("D:\\workspace\\TrafficSys\\WebRoot\\WEB-INF\\sysConfig.xml");
			file.write(result, 0, result.length());
			file.flush();

		} catch (Exception w) {
			w.printStackTrace();
		}
	}
}
