package Web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @date 2016年4月13日 LoginFilter.java
 * @author CZP
 * @parameter
 */
public class LoginFilter implements Filter {

	private String[] excludedUrls;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludedUrl = filterConfig.getInitParameter("excludedUrl");
		if (excludedUrl != null) {
			excludedUrls = excludedUrl.split(",");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();
		String url = httpServletRequest.getRequestURI();
		for (String u : excludedUrls) {
			if (url.contains(u.trim())) {
				chain.doFilter(request, response);
			}
		}
		if (session.getAttribute("currentStudent") != null) {
			chain.doFilter(request, response);
		} else {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp");
		}
	}

	@Override
	public void destroy() {

	}

}
