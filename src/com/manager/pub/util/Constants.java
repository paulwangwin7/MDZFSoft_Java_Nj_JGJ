package com.manager.pub.util;

import com.manager.pub.bean.UploadForm;

public class Constants
{
	public static String JSP_MESSAGE = "jspInformation";//页面信息
	public static String JSP_ROLE_MANAGER_ACTION = "jspRoleManagerAction";//用户角色管理动作
	public static String JSP_ROLE_LIST = "jspRoleList";//用户角色列表
	public static String JSP_MENU_LIST = "jspMenuList";//用户菜单列表
	public static String JSP_ROLE_FORM = "jspRoleForm";//用户角色详情
	public static String JSP_TREE_LIST = "jspTreeList";//部门总列表
	public static String JSP_USER_LIST = "jspUserList";//用户信息列表
	public static String JSP_USER_FORM = "jspUserForm";//用户详细信息
	public static String JSP_USER_MANAGER_ACTION = "jspUserManagerAction";//用户角色管理动作
	public static String JSP_NOTICE_MANAGER_ACTION = "jspNoticeManagerAction";//公告管理动作
	public static String JSP_ServerInfo_LIST = "jspServerInfoList";//服务器监控详情列表
	public static String JSP_ServerInfo_FORM = "jspServerInfoForm";//服务器监控详情
	public static String JSP_UPLOAD_LIST = "jspUploadList";//上传文件日志详情列表
	public static String JSP_UPLOAD_FORM = "jspUploadForm";//上传文件日志详情
	public static String JSP_LOG_FORM = "jspActinoLogForm";//上传文件日志详情

	public static String SESSION_USER_FORM = "sessionUserForm";//用户详细信息 session
	public static String SESSION_ROLE_FORM = "sessionRoleForm";//角色详细信息 session
	public static String SESSION_URL_LIST = "sessionUrlList";//菜单列表详细信息 session
	public static String SESSION_NOTICE_LIST = "sessionNoticeList";//用户最新公告 session
	public static String SESSION_LOOKED_NOTICE = "sessionLookedNotice";//用户看过的公告 session

	public static String SESSION_ADMININFO = "sessionAdminInfo";//管理员详细信息 session
	public static String CHECK_CODE = "checkCode";//验证码

	public static int PAGE_LINES = 20;//每页显示数据的行数
	public static int PAGE_NUMS = 10;//每翻一页的页码数

	public static int ACTION_SUCCESS = 0;
	public static int ACTION_FAILED = -1;

	public static String PAGE_INFORMATION = "pageInformation";//页面分页信息

	public static UploadForm UPLOADFORM = null;//正在制作的文件对象

	/**
	 * sssss
	 * @param timeStr
	 * @param formatType 0-年月日 1-年月日时分秒
	 * @return
	 */
	public static String timeFormat(String timeStr, String formatType)
	{
		if(timeStr.length()<8)
		{
			return "";
		}
		else
		{
			if(formatType.equals("0"))
			{
				return timeStr.substring(0,4)
				+"-"+timeStr.substring(4,6)
				+"-"+timeStr.substring(6,8);
			}
			else if(formatType.equals("1"))
			{
				return timeStr.substring(0,4)
				+"-"+timeStr.substring(4,6)
				+"-"+timeStr.substring(6,8)
				+" "+timeStr.substring(8,10)
				+":"+timeStr.substring(10,12)
				+" "+timeStr.substring(12,14)+"'";
			}
			else
			{
				return timeStr;
			}
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println(timeFormat("20101130133553","1"));
	}
}
