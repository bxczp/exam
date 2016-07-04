package Util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * @date 2016年3月16日 ResponseUtil.java
 * @author CZP
 * @parameter
 */
public class ResponseUtil {

	public static void write(HttpServletResponse response, Object o) throws Exception {
		// flush() 之后就相当于是 response committed 了!!!!!
		response.setContentType("text/html;cherset=utf-8");
		PrintWriter write = response.getWriter();
		write.println(o.toString());
		write.flush();
		write.close();

	}

}
