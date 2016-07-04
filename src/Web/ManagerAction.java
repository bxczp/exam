package Web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import Dao.ManagerDao;
import Model.Manager;

/**
 * @date 2016年3月15日 ManagerAction.java
 * @author CZP
 * @parameter
 */
public class ManagerAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest req;
	private Manager manager = new Manager();
	private String mainPage;
	private ManagerDao managerDao = new ManagerDao();
	private String error;

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.req = request;

	}

	public String login() {
		HttpSession session = req.getSession();
		Manager currentManager = managerDao.login(manager);
		if (currentManager != null) {
			session.setAttribute("currentStudent", currentManager);
		} else {
			error = "用户不存在";
			return ERROR;
		}
		return SUCCESS;
	}

	public String logout() {
		HttpSession session = req.getSession();
		session.removeAttribute("currentStudent");
		session.invalidate();
		return "logout";
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
