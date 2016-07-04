package Web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import Dao.PaperDao;
import Dao.QuestionDao;
import Model.PageBean;
import Model.Paper;
import Model.Question;
import Util.PageUtil;
import Util.PropertyUtil;
import Util.ResponseUtil;
import Util.StringUtil;
import net.sf.json.JSONObject;

/**
 * @date 2016年3月16日 QuestionAction.java
 * @author CZP
 * @parameter
 */
public class QuestionAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest req;
	private QuestionDao questionDao = new QuestionDao();
	private String mainPage;
	private List<Question> questionList = new ArrayList<>();
	private Question question = new Question();
	private String page;
	private String pageCode;
	private String error;
	private Question s_question = new Question();
	private String questionId;
	private String title;
	private PaperDao paperDao = new PaperDao();
	private List<Paper> paperList = new ArrayList<>();

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.req = request;
	}

	public String list() {
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(PropertyUtil.getValue("pageSize")));
		try {
			questionList = questionDao.questionList(s_question, pageBean);
			int total = questionDao.questionListCount(s_question);
			String subject = s_question.getSubject();
			pageCode = PageUtil.genPagination("question!list?s_question.subject=" + subject, total,
					Integer.parseInt(page), Integer.parseInt(PropertyUtil.getValue("pageSize")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPage = "question/questionList.jsp";
		return SUCCESS;
	}

	public String getQuestionById() {
		try {
			question = questionDao.getQuestion(questionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPage = "question/questionShow.jsp";
		return "success";
	}

	public String preSave() {
		try {
			paperList = paperDao.getPaperList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (StringUtil.isNotEmpty(questionId)) {
			// 更新操作
			try {
				question = questionDao.getQuestion(questionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			title = "更新问题";
		} else {
			title = "添加问题";
		}
		mainPage = "question/questionSave.jsp";
		return "success";
	}

	public String save() {
		if (StringUtil.isNotEmpty(questionId)) {
			// 更新操作
			question.setId(Integer.parseInt(questionId));
			try {
				questionDao.questionUpdate(question);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			question.setJoinTime(new Date());
			try {
				questionDao.questionUpdate(question);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "questionSave";
	}

	public String delete() {
		JSONObject jsonObject = new JSONObject();
		try {
			question = questionDao.getQuestion(questionId);
			questionDao.questionDelete(question);
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

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public Question getS_question() {
		return s_question;
	}

	public void setS_question(Question s_question) {
		this.s_question = s_question;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Paper> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
	}

}
