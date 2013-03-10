package com.manager.pub.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class DateUtils
{
	/**
	 * 取当前时间的字符串，精确到分钟
	 * 
	 * @return 取当前时间字符串 具体格式自定 例如“yyyyMMdd”
	 */
	public static String getChar8()
	{
		return DateFormatUtils.format(new Date(), "yyyyMMdd");
	}

	/**
	 * 取当前时间的字符串，精确到分钟
	 * 
	 * @return 取当前时间字符串 具体格式自定 例如“yyyyMMddHHmm”
	 */
	public static String getChar12()
	{
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
	}

	/**
	 * 取当前时间的字符串，精确到分钟
	 * 
	 * @return 取当前时间字符串 具体格式自定 例如“yyyyMMddHHmmss”
	 */
	public static String getChar14()
	{
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 获取指定日期 向前或向后滚动特定小时数后的日期
	 * 
	 * @param dateNow
	 *            String 当前日期 'yyyyMMddHHmmss'
	 * @param rollDate
	 *            int 待滚动的天数
	 * @return String 指定日期 +/- 特定天数 后的日期（格式 CCYYMMDD）
	 */
	public static String rollHour(String dateNow, int rollDate)
	{
		String dateReturn = "";

		if (dateNow == null || dateNow.trim().length() < 14)
			return dateReturn;

		dateNow = dateNow.trim();
		Calendar cal = Calendar.getInstance();
		int nYear = Integer.parseInt(dateNow.substring(0, 4));
		int nMonth = Integer.parseInt(dateNow.substring(4, 6));
		int nDate = Integer.parseInt(dateNow.substring(6, 8));
		int nHour = Integer.parseInt(dateNow.substring(8, 10));
		int nMinute = Integer.parseInt(dateNow.substring(10, 12));
		int nSecond = Integer.parseInt(dateNow.substring(12, 14));
		cal.set(nYear, nMonth - 1, nDate, nHour, nMinute, nSecond);

		cal.add(Calendar.HOUR_OF_DAY, rollDate);

		String strYear = String.valueOf(cal.get(Calendar.YEAR));
		String strMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String strDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		String strHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		String strMinute = String.valueOf(cal.get(Calendar.MINUTE));
		String strSecond = String.valueOf(cal.get(Calendar.SECOND));
		strMonth = (strMonth.length() == 1) ? "0" + strMonth : strMonth;
		strDay = (strDay.length() == 1) ? "0" + strDay : strDay;
		strHour = (strHour.length() == 1) ? "0" + strHour : strHour;
		strMinute = (strMinute.length() == 1) ? "0" + strMinute : strMinute;
		strSecond = (strSecond.length() == 1) ? "0" + strSecond : strSecond;
		dateReturn = strYear + strMonth + strDay + strHour + strMinute
				+ strSecond;

		return dateReturn;
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
	 * 将当前日期向后滚动n个月
	 * 
	 * @param srcDate
	 *            String 当前日期
	 * @param rollMonth
	 *            String 待滚动的月数
	 * @return String
	 */
	public static String rollMonth(String srcDate, String rollMonth)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(srcDate.substring(0, 4)), Integer.parseInt(srcDate.substring(4, 6)) - 1, Integer.parseInt(srcDate.substring(6, 8)));
		cal.roll(Calendar.MONTH, Integer.parseInt(rollMonth));
		cal.roll(Calendar.YEAR, Integer.parseInt(rollMonth) / 12);
		return formatToChar8(cal);
	}

	/**
	 * 将当前日期向前滚动n个月
	 * 
	 * @param srcDate
	 *            String 当前日期
	 * @param rollMonth
	 *            String 待滚动的月数
	 * @return String
	 */
	public static String preMonth(String srcDate, String rollMonth)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(srcDate.substring(0, 4)), Integer.parseInt(srcDate.substring(4, 6)) - 1, Integer.parseInt(srcDate.substring(6, 8)));
		cal.add(Calendar.MONTH, Integer.parseInt(rollMonth));
		cal.add(Calendar.YEAR, Integer.parseInt(rollMonth) / 12);
		return formatToChar8(cal);
	}

	public static String formatToChar8(Calendar tmpcal)
	{
		String tmpYear = Integer.toString(tmpcal.get(Calendar.YEAR));
		String tmpMonth = Integer.toString(tmpcal.get(Calendar.MONTH) + 1);
		String tmpDay = Integer.toString(tmpcal.get(Calendar.DAY_OF_MONTH));
		String tmpDate = tmpYear
				+ (tmpMonth.length() == 1 ? "0" + tmpMonth : tmpMonth)
				+ (tmpDay.length() == 1 ? "0" + tmpDay : tmpDay);
		return tmpDate;
	}

	public static String formatChar14Time(String char14)
	{
		if(char14.length()==14)
		{
			return char14.substring(0,4)+"/"+char14.substring(4,6)+"/"+char14.substring(6,8)+" "+char14.substring(8,10)+":"+char14.substring(10,12)+":"+char14.substring(12,14);
		}
		else
		{
			return char14;
		}
	}

	/**
	 * 获取年月日星期几
	 */
	public static String getDayStr()
	{
		String dayStr = getChar8().substring(0,4)+"年"+getChar8().substring(4,6)+"月"+getChar8().substring(6,8)+"日 ";
		dayStr += "星期";
		switch(new Date().getDay())
		{
			case 1: dayStr += "一"; break;
			case 2: dayStr += "二"; break;
			case 3: dayStr += "三"; break;
			case 4: dayStr += "四"; break;
			case 5: dayStr += "五"; break;
			case 6: dayStr += "六"; break;
			case 7: dayStr += "日"; break;
			default:dayStr += "一"; break;
		}
		return dayStr;
	}

	/**
	 * 根据js获取char8日期
	 * @param dayStr mm/dd/yyyy
	 */
	public static String getChar8ByJs(String dayStr)
	{
		String dayString = getChar8();
		try
		{
			dayString = dayStr.split("/")[2]+dayStr.split("/")[0]+dayStr.split("/")[1];
		}
		catch(Exception ex)
		{
			
		}
		finally
		{
			return dayString;
		}
	}

	/**
	 * 根据日期某年某月获取该月总共多少天
	 * @param timeStr
	 * @return
	 */
	public static int getMonthDays(String timeStr)
	{
		int totalDays = 31;
		timeStr = timeStr.substring(0,6);
		int thisYear = Integer.parseInt(timeStr.substring(0,4));
		int thisMonth = Integer.parseInt(timeStr.substring(4,6));
		switch(thisMonth)
		{
			case 1 : totalDays = 31; break;
			case 2 : {
				if((thisYear%4==0&&thisYear%100!=0)||(thisYear%400==0))
				{
					totalDays = 29;
				}
				else
				{
					totalDays = 28;
				}
				break;
			}
			case 3 : totalDays = 31; break;
			case 4 : totalDays = 30; break;
			case 5 : totalDays = 31; break;
			case 6 : totalDays = 30; break;
			case 7 : totalDays = 31; break;
			case 8 : totalDays = 31; break;
			case 9 : totalDays = 30; break;
			case 10: totalDays = 31; break;
			case 11: totalDays = 30; break;
			case 12: totalDays = 31; break;
			default: ;
		}
		return totalDays;
	}

	/**
	 * 时间相减
	 * @param time
	 * @return
	 */
	public static int timeXJ(String time) {
		int xjMIN = 0;
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmm");
		try {
			Date date1 = inputFormat.parse(time);
			Date date2 = inputFormat.parse(getChar12());
			long mint=Math.abs((date1.getTime()-date2.getTime())/(1000));
			int hor=(int)mint/3600;
			int mins=(int)mint%3600/60;
			int secd=(int)mint%3600;
			int day=(int)hor/24;
			xjMIN = mins;
		} catch(Exception ex) {
			
		} finally {
			return xjMIN;
		}
	}

	public static void main(String[] args)
	{
		//System.out.print(rollHour("20100708000000",0));

//		String nowTime = DateFormatUtils.format(new Date(), "HH");
//		System.out.println(nowTime);
//		System.out.println(getChar12().substring(8, 10));
//		System.out.println(formatChar14Time("20101124111720"));
//		System.out.println(getDayStr());
//		System.out.println(getChar8ByJs("10/02/2020"));
//		System.out.println(rollDate(getChar8(), -6));
	}
}
