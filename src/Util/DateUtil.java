package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date 2016年3月15日 DateUtil.java
 * @author CZP
 * @parameter
 */
public class DateUtil {

	public static String formatDateToString(Date date, String patter) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(patter);
		if (date != null) {
			return format.format(date);
		} else {
			return "";
		}
	}

	public static Date formatStringToDate(String str, String patter) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(patter);
		if (StringUtil.isNotEmpty(str)) {
			return format.parse(str);
		} else {
			return null;
		}
	}

	public static String getCurrentdateString() throws Exception {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		return format.format(date);
	}

}
