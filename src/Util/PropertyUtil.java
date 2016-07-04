package Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @date 2016年3月15日 PropertyUtil.java
 * @author CZP
 * @parameter
 */
public class PropertyUtil {

	public static String getValue(String key) {
		Properties properties = new Properties();
		InputStream input = new PropertyUtil().getClass().getResourceAsStream("/exam.properties");
		try {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty(key);
	}

}
