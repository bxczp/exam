package Web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import Dao.ExamDao;
import Dao.QuestionDao;
import Model.Exam;
import Model.PageBean;
import Model.Question;
import Util.PageUtil;
import Util.PropertyUtil;
import Util.StringUtil;

/**
 * @date 2016年3月15日 ExamAction.java
 * @author CZP
 * @parameter
 */
public class ExamAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest req;
	private Question question;
	private QuestionDao questionDao = new QuestionDao();
	private ExamDao examDao = new ExamDao();
	private String mainPage;
	private Exam exam = new Exam();
	private Exam s_exam = new Exam();
	private List<Exam> examList = new ArrayList<>();
	private String pageCode;
	private String page;

	public String add() {
		// 获取所有的请求参数
		Map<String, String[]> keyMap = req.getParameterMap();
		Iterator<Entry<String, String[]>> iterator = keyMap.entrySet().iterator();
		int totalScore = 0;
		int singleScore = 0;
		int moreScore = 0;
		while (iterator.hasNext()) {
			Entry<String, String[]> entry = iterator.next();
			String key = entry.getKey();
			String[] values = entry.getValue();
			if (key.equals("exam.student.id") || key.equals("exam.paper.id")) {
				continue;
			}
			// 传入的Id值
			String k;
			// 传入的用户的选择答案
			String value = "";
			// 单选题
			if (key.split("-")[1].equals("r")) {
				k = key.split("-")[2];
				value = values[0];
				singleScore += this.calScore(k, value, "1");
			} else if (key.split("-")[1].equals("c")) {
				// 多选题
				k = key.split("-")[2];
				for (String str : values) {
					value += str + ",";
				}
				value = value.substring(0, value.length() - 1);
				moreScore += this.calScore(k, value, "2");
			}
		}
		totalScore = singleScore + moreScore;
		exam.setSingleScore(singleScore);
		exam.setMoreScore(moreScore);
		exam.setScore(totalScore);
		exam.setExamDate(new Date());
		try {
			examDao.saveExam(exam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPage = "exam/examResult.jsp";
		return SUCCESS;
	}

	public String getExams() {
		try {
			examList = examDao.getExamList(s_exam, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPage = "exam/myExam.jsp";

		return SUCCESS;
	}

	public String list() {
		int total = 0;
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		try {
			total = examDao.getExamListCount(s_exam);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(PropertyUtil.getValue("pageSize")));
		try {
			examList = examDao.getExamList(s_exam, pageBean);
			if (s_exam != null) {
				if (s_exam.getStudent() != null) {
					String id = s_exam.getStudent().getId();
					String name = s_exam.getStudent().getName();
					pageCode = PageUtil.genPagination(
							"exam!list?s_exam.student.id=" + id + "&s_exam.student.name=" + name, total,
							Integer.parseInt(page), Integer.parseInt(PropertyUtil.getValue("pageSize")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPage = "exam/examList.jsp";

		return SUCCESS;
	}

	private int calScore(String questionId, String stuAnswer, String type) {
		try {
			question = questionDao.getQuestion(questionId);
			if (stuAnswer.equals(question.getAnswer())) {
				if (type.equals("1")) {
					// 单选题
					return 20;
				} else {
					return 30;
				}
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
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

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Exam getS_exam() {
		return s_exam;
	}

	public void setS_exam(Exam s_exam) {
		this.s_exam = s_exam;
	}

	public List<Exam> getExamList() {
		return examList;
	}

	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

}
