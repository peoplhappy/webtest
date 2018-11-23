package org.jtest.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static String getCurrentTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String time = sdf.format(d);
		return time;
	}

	/**
	 * 获取距今多少天后的时间
	 * 
	 * @param day
	 *            若为正数则取之后的，若为负数则取之前的
	 * @return
	 */
	public static String getLastedTime(Object day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int days = Integer.valueOf(day.toString());
		Date d = new Date();
        return sdf.format(getDateBeforeAfter(d,days));
	}

	/**
	 * 得到几天前或几天后的时间的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBeforeAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	
	public static void main(String[] args){ 
        String afterdate=getLastedTime("3");
        System.out.println(afterdate);  
        

	}
}
