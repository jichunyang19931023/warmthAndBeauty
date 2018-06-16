package com.cn.beauty.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.cn.beauty.controller.UserController;

/**
 * 日期工具类
 * 
 * @author twy
 * 
 */
public class DateUtil {

	public static final String dateFormatType = "yyyy-MM-dd";
	public static final String dateFormat_shift = "yyyyMMdd";
	public static final String dateFormat_ss = "yyyy-MM-dd HH:mm:ss";

	private static TimeZone tz = TimeZone.getTimeZone("GMT+8");
	// FastDateFormat线程安全
	private static FastDateFormat fdfWithTime = FastDateFormat.getInstance(dateFormat_ss, tz, Locale.CHINA);
	private static FastDateFormat fdfWithoutTime = FastDateFormat.getInstance(dateFormatType, tz, Locale.CHINA);
	private static Logger logger = Logger.getLogger(String.valueOf(UserController.class));
	/**
	 * 获取当前时间，不带时分秒
	 * 
	 * @return String
	 */
	public static String getNowDateStringWithTime() {
		return fdfWithTime.format(new Date());
	}

	public static long timeBeforeDay(int day) {
		long longTime = (new Date()).getTime() / 1000;
		long minus = day * 24 * 3600;
		return longTime - minus;
	}

	public static String long2StringDate(long time, String format) {
		if (UtilValidate.isEmpty(format)) {
			return fdfWithTime.format(time); 
		}
		SimpleDateFormat sf = new SimpleDateFormat(format);
		sf.setTimeZone(tz);
		return sf.format(long2Date(time));
	}

	public static Date long2Date(long time) {
		return new Date(time * 1000);
	}
	
	/**
	 * 将java.sql.timestamp转为只有日期的String
	 * 
	 * @param timestamp
	 * @return String yyyy-MM-dd
	 */
	public static String timeStamp2StringNoTime(Timestamp timestamp) {
		return fdfWithoutTime.format(timestamp);
	}

	public static Date getDateBeforeSeconds(int sec) {
		return new Date(getTimeLongBeforeSeconds(sec));
	}
	
	public static long getTimeLongBeforeSeconds(int sec){
		return (new Date().getTime() - 1000L * sec);
	}

	/**
	 * 获取24小时前的时间
	 * 
	 * @return String
	 */
	public static String get24hAgo() {
		return fdfWithTime.format(getDateBeforeSeconds(24 * 60 * 60));
	}

	public static String get24hAgoNoTime() {
		return fdfWithoutTime.format(getDateBeforeSeconds(24 * 60 * 60));
	}

	public static long get24hAgoLong() {
		return getTimeLongBeforeSeconds(24 * 60 * 60);
	}

	/**
	 * 获得当前日期与本周日相差的天数
	 * 
	 * @return int
	 */
	private static int getMondayPlus() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(tz);
		// 获得今天是一周的第几天，星期日是第一天，星期一是第二天......
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 获得当前日期与本周日相差的天数
	 * 
	 * @return int
	 */
	private static int getMondayPlus(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(tz);
		calendar.setTime(date);
		// 获得今天是一周的第几天，星期日是第一天，星期一是第二天......
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 获得本周一零时的时间(String类型)
	 * 
	 * @return String
	 */
	public static String getMondayStringOFWeek() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeZone(tz);
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		String preMonday = fdfWithoutTime.format(monday);
		String[] arr_time = preMonday.split("-");
		if (arr_time[1].length() == 1) {
			arr_time[1] = "0" + arr_time[1];
		}
		if (arr_time[2].length() == 1) {
			arr_time[2] = "0" + arr_time[2];
		}
		preMonday = arr_time[0] + "-" + arr_time[1] + "-" + arr_time[2];
		// preMonday = preMonday + " 00:00:00";
		return preMonday;
	}

	/**
	 * 获得本周一日期(String类型 yyyy-MM-dd)
	 * 
	 * @return String
	 */
	public static String getFirstDayInWeek() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeZone(tz);
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		return date2String(monday, "");
	}

	/**
	 * 获得本周一日期(String类型 yyyy-MM-dd)
	 * 
	 * @return String
	 */
	public static String getFirstDayInWeek(String dateStr) {
		Date date = string2UtilDate(dateStr, dateFormatType);
		int mondayPlus = getMondayPlus(date);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTimeZone(tz);
		currentDate.setTime(date);
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		return date2String(monday, "");
	}

	/**
	 * 获得本周一零时的时间(Date类型)
	 * 
	 * @return Date
	 */
	public static Date getMondayDateOFWeek() {
		String preMonday = getMondayStringOFWeek();
		Date mondayDate = null;
		try {
			// SimpleDateFormat不是线程安全的，所以在此new一个实例使用
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat_ss);
			sdf.setTimeZone(tz);
			mondayDate = sdf.parse(preMonday);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return mondayDate;
	}

	/**
	 * 获取当月第一天零时的时间(String类型)
	 * 
	 * @return String
	 */
	public static String getFirstDayStringOfMonth() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeZone(tz);
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = fdfWithoutTime.format(lastDate.getTime());
		str = str + " 00:00:00";
		return str;
	}

	/**
	 * 获取当月第一天日期(String类型 yyyy-MM-dd)
	 * 
	 * @return String
	 */
	public static String getFirstDayInMonth() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTimeZone(tz);
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		String str = fdfWithoutTime.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取当月第一天零时的时间(Date类型)
	 * 
	 * @return Date
	 */
	public static Date getFirstDayDateOfMonth() {
		String str = getFirstDayStringOfMonth();
		Date firstDayDate = null;
		try {
			// SimpleDateFormat不是线程安全的，所以在此new一个实例使用
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat_ss);
			sdf.setTimeZone(tz);
			firstDayDate = sdf.parse(str);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return firstDayDate;
	}

	/**
	 * 将java.sql.timestamp转为带时间的String
	 * 
	 * @param timestamp
	 * @return String
	 */
	public static String timestamp2String(Timestamp timestamp) {
		return fdfWithTime.format(timestamp);
	}

	/**
	 * 将java.sql.Date转化为不带时间的String
	 * 
	 * @param date
	 * @return String
	 */
	public static String date2StringWithoutTime(java.sql.Date date) {
		return fdfWithoutTime.format(date);
	}
	
	/**
	 * 将java.sql.Date转化为不带时间的String
	 * 
	 * @param date
	 * @return String
	 */
	public static String date2StringWithTime(Date date) {
		if(date == null){
			return null;
		}
		return fdfWithTime.format(date);
	}

	/**
	 * 将String转换为java.sql.Date
	 * 
	 * @param strDate
	 * @param formatType
	 * @return
	 * @throws Exception
	 */
	public static java.sql.Date string2SqlDate(String strDate, String formatType) throws Exception {
		try {
			if (UtilValidate.isEmpty(formatType)) {
				formatType = dateFormatType;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(formatType);
			sdf.setTimeZone(tz);
			java.util.Date date = sdf.parse(strDate);
			long l = date.getTime();
			java.sql.Date sDate = new java.sql.Date(l);
			return sDate;
		} catch (ParseException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 将String转换为java.util.Date
	 * 
	 * @param strDate
	 * @param formatType
	 * @return
	 * @throws Exception
	 */
	public static Date string2UtilDate(String strDate) {
		if(strDate == null) return null;
		try {
			if(strDate.length() == 10){
				strDate += " 00:00:00";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat_ss);
			sdf.setTimeZone(tz);
			Date date = sdf.parse(strDate);
			return date;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 将String转换为java.util.Date
	 * 
	 * @param strDate
	 * @param formatType
	 * @return
	 * @throws Exception
	 */
	public static Date string2UtilDate(String strDate, String formatType) {
		if(strDate == null) return null;
		try {
			if (UtilValidate.isEmpty(formatType)) {
				formatType = dateFormatType;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(formatType);
			sdf.setTimeZone(tz);
			Date date = sdf.parse(strDate);
			return date;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public static String getDateStrFromTime(Integer sec) {
		if (sec == null) {
			return "";
		}
		return date2String(new Date(sec * 1000L), dateFormat_ss);
	}

	/**
	 * 取得字符串日期对应的时间(转换到秒)
	 * 
	 * @param strDate
	 * @param formatType
	 * @return
	 * @throws Exception
	 */
	public static long getTimeFromDatestr(String strDate, String formatType) {
		return string2UtilDate(strDate, formatType).getTime() / 1000;
	}
	
	/**
	 * 将java.util.Date转换为formatType格式的String
	 * 
	 * @param date
	 * @param formatType
	 * @return
	 * @throws Exception
	 */
	public static String date2String(Date date, String formatType) {
		String strDate = null;
		if(UtilValidate.isNotEmpty(date)){
			if (UtilValidate.isEmpty(formatType)) {
				formatType = dateFormatType;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(formatType);
			sdf.setTimeZone(tz);
			strDate = sdf.format(date);
		}
		return strDate;
	}
	
	/**
	 * 获得今日零时时间(String类型 yyyy-MM-dd HH:mm:ss)
	 * 
	 * @return String
	 */
	public static String getTodayStartString() {
		Date today = new Date();
		String str = "";
		str = fdfWithoutTime.format(today);
		str = str + " 00:00:00";
		return str;
	}

	public static Date getTodayStart() {
		return dayStart(new Date());
	}

	public static Date dayStart(Date date) {
		try {
			String str = fdfWithoutTime.format(date);
			str = str + " 00:00:00";
			return string2UtilDate(str, dateFormat_ss);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 取得当前日期，String格式
	 * 
	 * @param formatType
	 *            返回日期的格式，如yyyyMMdd
	 * @return
	 */
	public static String getNowDateString(String formatType) {
		if (UtilValidate.isEmpty(formatType)) {
			formatType = dateFormatType;
		}
		return date2String(new Date(), formatType);
	}

	/**
	 * 将inFormatType格式的日期字符串转换为outFormatType格式的日期字符串
	 * 
	 * @param strDate
	 * @param inFormatType
	 * @param outFormatType
	 * @return
	 * @throws Exception
	 */
	public static String string2DateString(String strDate, String inFormatType, String outFormatType) throws Exception {
		if (UtilValidate.isEmpty(outFormatType)) {
			outFormatType = dateFormatType;
		}
		java.util.Date date = string2UtilDate(strDate, inFormatType);
		return date2String(date, outFormatType);
	}

	/**
	 * 取得系统时间往后duration小时的时间（日期类型）
	 * 
	 * @return
	 */
	public static java.sql.Date getDateAfterDuration(long duration) {
		Date date = new Date();
		long Time = date.getTime() + 1000 * 60 * 60 * duration;

		java.sql.Date sDate = new java.sql.Date(Time);
		return sDate;
	}

	/**
	 * 取得系统时间往前duration小时的时间（日期类型）
	 * 
	 * @return
	 */
	public static java.sql.Date getDateBeforeDuration(long duration) {
		java.util.Date date = new Date();
		long Time = date.getTime() - 1000 * 60 * 60 * duration;

		java.sql.Date sDate = new java.sql.Date(Time);
		return sDate;
	}

	/**
	 * 取得系统时间往前duration小时的时间（字符串类型）
	 * 
	 * @return
	 */
	public static String getDateBeforeDuration2String(long duration) {
		java.util.Date date = new Date();
		long Time = date.getTime() - 1000 * 60 * 60 * duration;

		java.sql.Date sDate = new java.sql.Date(Time);
		return date2String(sDate, dateFormat_ss);
	}
	
	/**
	 * 取得指定时间后的日期
	 * @param dateStr
	 * @param duration
	 * @return
	 * @throws Exception 
	 */
	public static String getDateAfterDuration2String(String dateStr,long duration) throws Exception {
		Date date = string2UtilDate(dateStr, dateFormatType);
		long Time = date.getTime() + 1000 * 60 * 60 * duration;

		java.sql.Date sDate = new java.sql.Date(Time);
		return date2String(sDate, dateFormat_ss);
	}

	/**
	 * 取得系统时间往后duration小时的时间（字符串类型）
	 * 
	 * @param formatType
	 *            指定返回日期格式
	 * @return
	 */
	public static String getDateAfterDuration(long duration, String formatType) {
		if (UtilValidate.isEmpty(formatType)) {
			formatType = dateFormatType;
		}
		Date date = new Date();
		long Time = date.getTime() + 1000 * 60 * 60 * duration;

		date = new java.util.Date(Time);
		return date2String(date, formatType);
	}

	/**
	 * 取得7天前的时间（7*24小时前）
	 * 
	 * @return
	 */
	public static String getDateAfterWeek() {
		return getDateAfterDuration(-7 * 24, dateFormat_ss);
	}

	/**
	 * 取两个时间的时间差(单位毫秒)
	 * 
	 * @return
	 */
	public static long getDateDuration(String startTime, String endTime) {
		try {
			Date sDate = string2UtilDate(startTime, dateFormat_ss);
			Date eDate = string2UtilDate(endTime, dateFormat_ss);
			return eDate.getTime() - sDate.getTime();
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0L;
		}
	}

	/**
	 * 取两个时间的时间差(单位小时)
	 * 
	 * @return
	 */
	public static int getDateDurationHour(String startTime, String endTime) {
		try {
			Date sDate = string2UtilDate(startTime, "yyyy-MM-dd HH");
			Date eDate = string2UtilDate(endTime, "yyyy-MM-dd HH");
			return (int)((eDate.getTime() - sDate.getTime()) / 60 / 1000 / 60);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取一周前的开始日期
	 * 
	 * @return
	 */
	public static String getDateBeforWeek() {
		Calendar now = Calendar.getInstance();
		now.setTimeZone(tz);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - 6);
		return fdfWithoutTime.format(now.getTime());

	}

	/**
	 * 获取一个月前的开始日期
	 * 
	 * @return
	 */
	public static String getDateBeforMonth() {
		Calendar now = Calendar.getInstance();
		now.setTimeZone(tz);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) - 1);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + 1);
		return fdfWithoutTime.format(now.getTime());

	}

	/**
	 * 获取当前的日期
	 * 
	 * @return
	 */
	public static String getNowDateStringNoTime() {
		return fdfWithoutTime.format(new Date());
	}

	/**
	 * 获取当前时间
	 * 
	 * @return String
	 */
	public static String getNowDayStringNoSplit() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		sdf.setTimeZone(tz);
		return sdf.format(new Date());
	}

	/**
	 * 获取指定日期到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return Date
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTimeZone(tz);
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 获取指定日期到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return Date
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTimeZone(tz);
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 以字符串形式获取指定日期到几天的时间
	 * 
	 * @author rainyhao
	 * @since 2013-11-20 上午11:53:06
	 * @param d
	 * @param pattern
	 * @param day
	 * @return
	 */
	public static String getDateBeforeForString(Date d, String pattern, int day) {
		Date r = getDateBefore(d, day);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(tz);
		return format.format(r);
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isIn7days(String date) {
		String week = getDateTimeBeforWeek();
		// System.out.println("mindate--"+getMinDate(week,date));
		if (week.equals(getMinDate(week, date)))
			return true;
		else
			return false;
	}

	/**
	 * 获取两个日期中较小的日期
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String getMinDate(String date1, String date2) {
		// System.out.println("date1--"+date1+"--date2--"+date2);
		if(date1 != null && date2 != null){
			// 只比较到小时，解决请求产生的时间延迟
			String date11 = date1.split(":")[0];
			String date22 = date2.split(":")[0];
			if (date1 != null && date2 != null && !"".equals(date1) && !"".equals(date2)) {
				if (date11.compareTo(date22) <= 0) {
					return date1;
				} else {
					return date2;
				}
			}
		}
		return getNowDateStringWithTime();
	}

	/**
	 * 获取一周前的开始日期，带时间
	 * 
	 * @return
	 */
	public static String getDateTimeBeforWeek() {
		Calendar now = Calendar.getInstance();
		now.setTimeZone(tz);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - 7);
		return fdfWithTime.format(now.getTime());

	}

	/**
	 * 字符串时间转化为long类型时间
	 * 
	 * @param time
	 * @return
	 */
	public static long getLongTime(String time, String format) {
		if (UtilValidate.isEmpty(format)) {
			format = dateFormat_ss;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(tz);
		java.util.Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		long l = date.getTime() / 1000;
		return l;
	}

	/**
	 * 得到几天前的日期 格式如2012-07-13
	 * 
	 * @param day
	 * @return
	 * @author lizhenzhen 2016-6-2
	 */
	public static String getDayBeforeNoTime(int day) {
		return fdfWithoutTime.format(getDateBefore(new Date(), day));
	}

	public static String getDayBefore(int day) {
		String todayString = fdfWithTime.format(getDateBefore(new Date(), day));
		todayString = todayString.substring(0, 10) + " 00:00:00";
		return todayString;
	}
}
