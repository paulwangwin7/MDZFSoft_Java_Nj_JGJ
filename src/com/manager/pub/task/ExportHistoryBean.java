package com.manager.pub.task;

import java.util.TimerTask;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.manager.pub.bean.SystemConfig;
import com.manager.pub.util.HttpUtils;

public class ExportHistoryBean extends TimerTask {
	private boolean isRunning = false;
	private ServletContext context = null;
	protected static final Logger logger = Logger.getLogger(ExportHistoryBean.class);

	public ExportHistoryBean(ServletContext context) {
		this.context = context;
	}

	public void run() {
		if (!isRunning) {
			isRunning = true;
			context.log("开始执行指定任务");
			logger.info("开始执行指定任务 -- 服务器状态记录");
			String urlStr = SystemConfig.getSystemConfig().getListenUrl();
			System.out.println("urlStr======"+urlStr);
			String retstr = HttpUtils.callRPC(urlStr);
			System.out.println("retstr======"+retstr);
			if(retstr.equals("0"))
			{
				logger.info("开始执行指定任务 -- 记录成功");
			}
			else
			{
				logger.info("开始执行指定任务 -- 记录失败");
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