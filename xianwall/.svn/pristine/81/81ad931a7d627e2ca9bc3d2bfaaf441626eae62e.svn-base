package com.wanding.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {
	public static final String FORMATDATE = "yyyy-MM-dd";
	public static final String FORMATDATENUM = "yyyyMMdd";
	public static final String FORMATDATE2 = "yyyyMM";
	public static final String FORMATDATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMATDATETIME2 = "yyyyMMddHHmmss";

	public static String format(Timestamp timestamp, String format) {
		if (timestamp == null) {
			return "";
		}
		return new SimpleDateFormat(format)
				.format(new Date(timestamp.getTime()));
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(format).format(date);
	}

	public static Date parse(String date, String format) {
		if (date == null) {
			return null;
		}
		DateFormat fmt = new SimpleDateFormat(format);

		Date date2;
		try {
			date2 = fmt.parse(date);
		} catch (ParseException e) {
			return null;
		}

		return date2;
	}

	public static long diffDays(Date date1, Date date2) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATDATE);
		try {
			date1 = sdf.parse(sdf.format(date1));
			date2 = sdf.parse(sdf.format(date2));
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			long time1 = cal.getTimeInMillis();
			cal.setTime(date2);
			long time2 = cal.getTimeInMillis();
			long between_days = (time1 - time2) / (1000 * 3600 * 24);
			return between_days;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
