package com.manager.pub.util;

import java.util.Timer;   

import javax.servlet.ServletContextEvent;   
import javax.servlet.ServletContextListener;   

import org.apache.log4j.Logger;

import com.manager.pub.bean.SystemConfig;
import com.manager.pub.task.AVItoFLV;
import com.manager.pub.task.ExportHistoryBean;
import com.manager.pub.task.FlvDone;


public class TimerTask implements ServletContextListener   
{
	protected static final Logger logger = Logger.getLogger(TimerTask.class);
	private Timer timer = null;

	public void contextInitialized(ServletContextEvent event)   
    {   
    	logger.info("-----------------监听器启动-----------------------");   
//      在这里初始化监听器，在tomcat启动的时候监听器启动，可以在这里实现定时器功能   
        timer = new Timer(true);   
        logger.info("---------------------------------------------------");   
        event.getServletContext().log("定时器已启动");//添加日志，可在tomcat日志中查看到
        //System.out.println("Integer.parseInt(DateUtils.getChar12().substring(8,10))"+Integer.parseInt(DateUtils.getChar12().substring(8,10)));
//        if(Integer.parseInt(DateUtils.getChar12().substring(8,10))>=8 && Integer.parseInt(DateUtils.getChar12().substring(8,10))<=21)
//        {
        	timer.schedule(new ExportHistoryBean(event.getServletContext()),0l,(60*60*1000));
//      调用exportHistoryBean，0表示任务无延迟，5*1000表示每隔5秒执行任务，60*60*1000表示一个小时。
//        }
        if(SystemConfig.getSystemConfig().getFileSystemDel().equals("Y")){
        	timer.schedule(new FileSystemDel(event.getServletContext()),0l,(24*60*60*1000));//定期删除过期文件
        }
        event.getServletContext().log("已经添加任务");
        //添加系统删除 设置一个开关键 根据条件判断删除 ！！！！！！！！！！！

        //new AVItoFLV().start();//正在实现 90%
        //new FlvDone().start();//正在实现 90%
    }
    public void contextDestroyed(ServletContextEvent event)   
    {   
//      在这里关闭监听器，所以在这里销毁定时器。   
        timer.cancel();   
        event.getServletContext().log("定时器销毁");   
    }   
}