package com.manager.pub.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 
 * @title:时间日期工具类
 * @description:
 * 
 * @author: Yao
 * 
 */
public final class DateTimeUtil
{
	/**
	 * 获得8位的日期 yyyyMMdd
	 * 
	 * @return
	 */
	public static String getChar8()
	{
		return DateFormatUtils.format(new Date(), "yyyyMMdd");
	}

	/**
	 * 获得14位的日期 yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getChar17()
	{
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
	}

	/**
	 * 获得14位的日期 yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getChar14()
	{
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 获得12位的日期 yyyyMMddHHmm
	 * 
	 * @return
	 */
	public static String getChar12()
	{
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
	}

	/**
	 * 格式化14位的日期
	 * 
	 * @param char14
	 * @return
	 */
	public static String formatChar14(String char14)
	{
		if (char14 == null || char14.length() == 0)
			return char14;
		return char14.substring(0, 4) + "-" + char14.substring(4, 6) + "-" + char14.substring(6, 8) + " "
				+ char14.substring(8, 10) + ":" + char14.substring(10, 12) + ":" + char14.substring(12, 14) + " ";
	}

	/**
	 * 格式化8位的日期
	 * 
	 * @param char8
	 * @return
	 */
	public static String formatChar8(String char8)
	{
		if (char8 == null || char8.length() == 0)
			return char8;
		return char8.substring(0, 4) + "-" + char8.substring(4, 6) + "-" + char8.substring(6, 8) + " ";

	}

	public static String formatDataBaseChar8(String char8)
	{
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd ");
		String time = null;
		try
		{
			time = DateFormatUtils.format(formate.parse(char8), "yyyyMMdd");
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}

	public static String formatDataBaseChar12(String char8)
	{
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = null;
		try
		{
			time = DateFormatUtils.format(formate.parse(char8), "yyyyMMddHHmm");
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 返回当天所在的年份
	 * 
	 * @return yyyy
	 */
	public static String getCurrentYear()
	{
		return getChar8().substring(0, 4);
	}

	/**
	 * 返回当天所在的年月
	 * 
	 * @return yyyyMM
	 */
	public static String getCurrentYearMonth()
	{
		return getChar8().substring(0, 6);
	}

	/**
	 * 返回当天所在的月份
	 * 
	 * @return mm
	 */
	public static String getCurrentMonth()
	{
		return getChar8().substring(4, 6);
	}

	/**
	 * 返回指定时间所在月份
	 * 
	 * @param date
	 * @return
	 */
	public static String getAssignMonth(String date)
	{
		if (date != null && date.length() >= 6)
			return date.substring(4, 6);

		return null;
	}

	/**
	 * 根据指定规则格式化日期
	 * 
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String formatDate(String date, String formatter)
	{
		SimpleDateFormat myFormatter = null;
		Date da = null;
		if (date.length() < 15)
			myFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
		else
			myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			da = myFormatter.parse(date);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return DateFormatUtils.format(da, formatter);
	}

	public static Date parse(String dateStr, String formatter)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatter);
		try
		{
			return format.parse(dateStr);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将当前日期向后滚动n天
	 * @param dateNow
	 * @param rollDate
	 * @return
	 */
	public static String rollDate(String dateNow, int rollDate) {
		String dateReturn = "";
		if (dateNow == null || dateNow.trim().length() < 8)
			return dateReturn;

		dateNow = dateNow.trim();
		Calendar cal = Calendar.getInstance();
		int nYear = Integer.parseInt(dateNow.substring(0, 4));
		int nMonth = Integer.parseInt(dateNow.substring(4, 6));
		int nDate = Integer.parseInt(dateNow.substring(6, 8));
		cal.set(nYear, nMonth - 1, nDate);
		cal.add(Calendar.DATE, rollDate);
		String strYear = String.valueOf(cal.get(Calendar.YEAR));
		String strMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String strDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		strMonth = (strMonth.length() == 1) ? "0" + strMonth : strMonth;
		strDay = (strDay.length() == 1) ? "0" + strDay : strDay;
		dateReturn = strYear + strMonth + strDay;
		return dateReturn;
	}
	/**
	 * 获取月份的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static int lastDay(Integer year, int month) {
		  
		Calendar cal = Calendar.getInstance(); 
		cal.set(year, month - 1, 1); 
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1); 
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public static void main(String args[]){
		System.out.println(lastDay(2013,2));
	}
}
