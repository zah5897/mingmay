package com.mingmay.cc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;

public class TimeUtil {
	public TimeUtil(Context paramContext) {
	}
	public static String currentLocalTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return sdf.format(System.currentTimeMillis());
	}
	public static String currentLocalDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(System.currentTimeMillis());
	}
	public static String currentLocalCNDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(System.currentTimeMillis());
	}

	public String getCurrenMinute() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("mm", localCalendar).toString();
	}

	public static String currentSplitTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(System.currentTimeMillis());
	}
	public static String currentSplitTimeString(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime;
		try {
			datetime = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			datetime=new Date();
		}
		sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(datetime);
	}
	
	public String getCurrentDay() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("dd", localCalendar).toString();
	}

	public String getCurrentHour() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("kk", localCalendar).toString();
	}

	public String getCurrentMonth() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("MM", localCalendar).toString();
	}

	public String getCurrentSecond() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("ss", localCalendar).toString();
	}

	public String getCurrentYear() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("yyyy", localCalendar).toString();
	}

	public String getDay(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("dd", localCalendar).toString();
	}

	public String getHour(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("kk", localCalendar).toString();
	}

	public String getMinute(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("mm", localCalendar).toString();
	}

	public String getMonth(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("MM", localCalendar).toString();
	}

	public String getSecond(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("ss", localCalendar).toString();
	}

	public String getTime(String paramString) {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format(paramString, localCalendar).toString();
	}

	public String getTime(String paramString, Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format(paramString, localCalendar).toString();
	}

	public String getYear(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("yyyy", localCalendar).toString();
	}
	public static int compareTime(String startTime,String endTime){
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			long start=sdf.parse(startTime).getTime();
			long end=sdf.parse(endTime).getTime();
			long now=System.currentTimeMillis();
			if(start<=now&&now<end){
				return 0;
			}else if(start>now){
				return -1;
			}else if(now>=end){
				return 1;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2;
	}
	
}

/*
 * Location: C:\Documents and Settings\Administrator\桌面\classes_dex2jar.jar
 * Qualified Name: org.dns.framework.util.TimeUtil JD-Core Version: 0.6.0
 */