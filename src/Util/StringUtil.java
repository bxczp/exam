package Util;

/**
 * @date 2016年3月15日 StringUtil.java
 * @author CZP
 * @parameter
 */
public class StringUtil {

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str)) {
			return true;
		} else {
			return false;
		}
	}
}
