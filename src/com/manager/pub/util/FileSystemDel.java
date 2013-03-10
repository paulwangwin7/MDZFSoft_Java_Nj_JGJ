package com.manager.pub.util;

import java.util.TimerTask;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.manager.pub.bean.SystemConfig;

public class FileSystemDel extends TimerTask {
	private static boolean isRunning = false;
	private ServletContext context = null;
	protected static final Logger logger = Logger.getLogger(FileSystemDel.class);

	public FileSystemDel(ServletContext context) {
		this.context = context;
	}

	public void run() {
		if (!isRunning) {
			isRunning = true;
			context.log("开始执行指定任务");
			logger.info("开始执行指定任务 -- 上传文件过期删除记录");
			String urlStr = SystemConfig.getSystemConfig().getFileSavePath()+"/sysAction.do?method=fileSystemDel";
			String retstr = HttpUtils.callRPC(urlStr);
			System.out.println("retstr==="+retstr);
			if(retstr.equals("0"))
			{
				logger.info("开始执行指定任务 -- 删除成功");
			}
			else
			{
				logger.info("开始执行指定任务 -- 删除失败");
			}
			// 在这里编写自己的功能，例：
			// File file = new File("temp");
			// file.mkdir();
			// 启动tomcat，可以发现在tomcat根目录下，会自动创建temp文件夹

			// -------------------结束
			isRunning = false;
			context.log("指定任务执行结束");
		} else {
			context.log("上一次任务执行还未结束");
		}
	}
}