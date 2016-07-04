package Web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import Dao.StudentDao;
import Model.PageBean;
import Model.Student;
import Util.DateUtil;
import Util.PageUtil;
import Util.PropertyUtil;
import Util.ResponseUtil;
import Util.StringUtil;
import net.sf.json.JSONObject;

/**
 * @date 2016年3月14日 StudentAction.java
 * @author CZP
 * @parameter
 */
public class StudentAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest req;
	private StudentDao studentDao = new StudentDao();
	private Student student = new Student();
	private String error;
	private String mainPage;
	private String studentId;
	private List<Student> studentList = new ArrayList<>();
	private Student s_student = new Student();
	private String page;
	private String pageCode;
	private String title;

	public String preUpdate() {
		mainPage = "/student/updatePassword.jsp";
		return SUCCESS;
	}

	public String modifyPassword() {
		try {
			Student s = studentDao.getStduentById(student.getId());
			s.setPassword(student.getPassword());
			studentDao.saveStudent(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPage = "/student/updateSuccess.jsp";
		return SUCCESS;
	}

	public String logout() {
		HttpSession session = req.getSession();
		session.removeAttribute("currentStudent");
		session.invalidate();
		return "logout";
	}

	public String list() {
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		int total = 0;
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(PropertyUtil.getValue("pageSize")));
		if (s_student != null) {
			studentList = studentDao.getStudentList(s_student, pageBean);
			total = studentDao.getStudentCount(s_student);
		} else {
			studentList = studentDao.getStudentList(new Student(), pageBean);
			total = studentDao.getStudentCount(new Student());
		}
		mainPage = "student/studentList.jsp";
		String id = s_student.getId();
		String name = s_student.getName();
		pageCode = PageUtil.genPagination(
				req.getContextPath() + "/student!list?s_student.id=" + id + "&s_student.name=" + name, total,
				Integer.parseInt(page), Integer.parseInt(PropertyUtil.getValue("pageSize")));
		return "success";
	}

	public Student getStudent() {
		return student;
	}

	public String preSave() {
		if (StringUtil.isEmpty(student.getId())) {
			title = "添加学生信息";
		} else {
			try {
				student = studentDao.getStduentById(student.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			title = "更新学生信息";
		}

		mainPage = "student/studentSave.jsp";

		return SUCCESS;
	}

	public String delete() {
		JSONObject jsonObject = new JSONObject();
		try {
			student = studentDao.getStduentById(student.getId());
			studentDao.studentDelete(student);
			jsonObject.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ResponseUtil.write(ServletActionContext.getResponse(), jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String saveStudent() {
		try {
			// 学生的id要自己生成
			if (StringUtil.isNotEmpty(student.getId())) {
				// 更新操作
				studentDao.saveStudent(student);

			} else {
				// 添加操作
				student.setId("JS" + DateUtil.getCurrentdateString());
				studentDao.saveStudent(student);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "saveStudent";
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String login() {
		HttpSession session = req.getSession();

		try {
			Student currentStudent = studentDao.login(student);
			if (currentStudent != null) {
				session.setAttribute("currentStudent", currentStudent);
			} else {
				error = "用户不存在";
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.req = request;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public Student getS_student() {
		return s_student;
	}

	public void setS_student(Student s_student) {
		this.s_student = s_student;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
