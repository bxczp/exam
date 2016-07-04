package Web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import Dao.PaperDao;
import Dao.QuestionDao;
import Model.Paper;
import Model.Question;
import Util.HibernateUtil;
import Util.ResponseUtil;
import Util.StringUtil;
import net.sf.json.JSONObject;

/**
 * @date 2016年3月14日 PaperAction.java
 * @author CZP
 * @parameter
 */
public class PaperAction extends ActionSupport implements ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest req;
	private String mainPage;
	private PaperDao paperDao = new PaperDao();
	private List<Paper> paperList = new ArrayList<>();
	private String paperId;
	private String page;
	private Paper paper = new Paper();
	private List<Question> squestionList = new ArrayList<>();
	private List<Question> mquestionList = new ArrayList<>();
	private QuestionDao questionDao = new QuestionDao();
	private String title;

	public String list() {
		try {
			paperList = paperDao.getPaperList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPage = "exam/selectPaper.jsp";
		return SUCCESS;
	}

	public String preSave() {
		if (StringUtil.isNotEmpty(paperId)) {
			// 更新操作
			title = "更新试卷信息";
			try {
				paper = paperDao.getPaper(paperId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			title = "添加试卷信息";
		}
		mainPage = "paper/paperSave.jsp";
		return SUCCESS;
	}

	public String save() {
		if (StringUtil.isNotEmpty(paperId)) {
			// 更新操作
			paper.setId(Integer.parseInt(paperId));
			paperDao.paperUpdate(paper);
		} else {
			paper.setJoinDate(new Date());
			paperDao.paperUpdate(paper);
		}
		return "paperSave";
	}

	public String paperList() {
		try {
			paperList = paperDao.getPaperList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPage = "paper/paperList.jsp";
		return SUCCESS;
	}

	public String deletePaper() {
		// 不能随便删除试卷 因为有问题和试卷关联
		JSONObject jsonObject = new JSONObject();
		try {
			if (questionDao.isExistByPaperId(paperId)) {
				jsonObject.put("error", "改试卷下存在问题，不能删除");
			} else {
				paper = paperDao.getPaper(paperId);
				paperDao.paperDelete(paper);
				jsonObject.put("success", true);
			}
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

	public String getDetailPaper() {
		try {
			paper = paperDao.getPaper(paperId);
			Set<Question> questionSet = paper.getQuestions();
			// 使用遍历器
			Iterator<Question> iterator = questionSet.iterator();
			while (iterator.hasNext()) {
				Question question = iterator.next();
				if ("1".equals(question.getType())) {
					squestionList.add(question);
				} else if ("2".equals(question.getType())) {
					mquestionList.add(question);
				}
			}
			// 获取随机题目
			squestionList = this.getRandomQuestion(squestionList, 3);
			mquestionList = this.getRandomQuestion(mquestionList, 2);
			mainPage = "exam/paper.jsp";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	private List<Question> getRandomQuestion(List<Question> questionList, int questionNum) {
		List<Question> resultList = new ArrayList<>();
		Random random = new Random();
		if (questionNum > 0) {
			for (int i = 0; i < questionNum; i++) {
				// 根据传入的list的长度取随机值
				int n = random.nextInt(questionList.size());
				Question q = questionList.get(n);
				// 如果重复了
				if (resultList.contains(q)) {
					i--;
				} else {
					resultList.add(q);
				}
			}
		}
		return resultList;
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

	public List<Paper> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public List<Question> getSquestionList() {
		return squestionList;
	}

	public void setSquestionList(List<Question> squestionList) {
		this.squestionList = squestionList;
	}

	public List<Question> getMquestionList() {
		return mquestionList;
	}

	public void setMquestionList(List<Question> mquestionList) {
		this.mquestionList = mquestionList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
