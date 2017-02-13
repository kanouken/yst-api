package org.common.tools.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日历帮助类
 * 
 * @author wangliang
 * 
 */
public class CalendarByTimeZoneHelper {
	public static int getCurrentMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH) + 1;
	}
	public static Date makeDate(Integer year, Integer month, Integer day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);

		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	public static String getAmOrPm(Date d) {

		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(d);
		return ca.get(GregorianCalendar.AM_PM) == 0 ? "AM" : "PM";
	}

	// 得到北京时间0点在GMT时间中的显示时间
	private static Calendar getGMTCalendarInCST() {
		// 计算CST今天在GMT中的时间区域
		Calendar c = Calendar.getInstance();
		if (c.get(Calendar.HOUR_OF_DAY) < 16) {
			c.add(Calendar.DAY_OF_MONTH, -1);
		}
		c.set(Calendar.HOUR_OF_DAY, 16);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	public static int getMonthDays(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M");
		String[] s = sdf.format(date).split("-");
		int y = Integer.valueOf(s[0]);
		int n = Integer.valueOf(s[1]);
		int total = 0;
		switch (n) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			total = 31;
			break;
		// 对于2月份需要判断是否为闰年
		case 2:
			if ((y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)) {
				total = 29;
				break;
			} else {
				total = 28;
				break;
			}
		case 4:
		case 6:
		case 9:
		case 11:
			total = 30;
			break;
		default:

			break;
		}

		return total;
	}

	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 得到CST时间的明天0点的GMT时间的显示
	 * 
	 * @return Date
	 */
	public static Date getTodayOClock() {
		return getGMTCalendarInCST().getTime();
	}

	/**
	 * 得到CST时间的明天0点的GMT时间的显示
	 * 
	 * @return Date
	 */
	public static Date getTomorrowOClock() {
		Calendar c = getGMTCalendarInCST();
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 得到CST时间的明天0点的GMT时间的显示
	 * 
	 * @return Date
	 */
	public static Date getTomorrow() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 得到CST时间的明天0点的GMT时间的显示
	 * 
	 * @return Date
	 */
	public static Date getSomedayOClock(int year, int month, int day) {
		Calendar c = getGMTCalendarInCST();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, day - 1);
		return c.getTime();
	}

	/**
	 * 得到CST时间的明天0点的GMT时间的显示
	 * 
	 * @return Date
	 */
	public static Date getSomedayNextOClock(int year, int month, int day) {
		Calendar c = getGMTCalendarInCST();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, day - 1);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	public static String getCSTDateString() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 8);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(c.getTime());
	}

	// public static int getComeLateMinites(Date signInDate) {
	// DateFormat df = new SimpleDateFormat("HH:mm");
	// Calendar c = Calendar.getInstance();
	// c.setTime(signInDate);
	// c.add(Calendar.HOUR_OF_DAY, 8);
	// try {
	// Date shouldTime = df.parse(Constants.startTime);
	// Date actualTime = df.parse(df.format(c.getTime()));
	// int lateTime = (int) ((actualTime.getTime() - shouldTime.getTime()) /
	// 1000 / 60);
	// if (lateTime > 0) {
	// return lateTime;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return 0;
	// }

	// public static int getLeaveEarlyMinites(Date signOutDate) {
	// DateFormat df = new SimpleDateFormat("HH:mm");
	// Calendar c = Calendar.getInstance();
	// c.setTime(signOutDate);
	// c.add(Calendar.HOUR_OF_DAY, 8);
	// try {
	// Date shouldTime = df.parse(Constants.endTime);
	// Date actualTime = df.parse(df.format(c.getTime()));
	// int lateTime = (int) ((shouldTime.getTime() - actualTime.getTime()) /
	// 1000 / 60);
	// if (lateTime > 0) {
	// return lateTime;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return 0;
	// }

	public static String getTimeDiff(Date startTime, Date endTime) {
		StringBuffer sb = new StringBuffer("");
		long day = 0l, hour = 0l, min = 0l, sec = 0l;
		long time1 = startTime.getTime();
		long time2 = endTime.getTime();
		long diff;
		diff = time2 - time1;
		day = diff / (24 * 60 * 60 * 1000);
		hour = (diff / (60 * 60 * 1000) - day * 24);
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		sb.append(day == 0 ? "" : day + "天");
		sb.append(hour == 0 ? "" : hour + "小时");
		sb.append(min == 0 ? "" : min + "分");
		return sb.toString();

	}

	public static Date getTimeAdd(Date start, Integer time, int minute) {
		Long _start = start.getTime(); // ms
		if (minute == Calendar.MINUTE) {
			Long diff = time * 60 * 1000l;
			return new Date(_start + diff);
		} else {
			return null;
		}
	}

	public static Date getMaxTime() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 9999);

		return c.getTime();
	}

}
