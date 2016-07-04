package Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import Model.PageBean;
import Model.Question;
import Util.HibernateUtil;
import Util.StringUtil;

/**
 * @date 2016年3月15日 QuestionDao.java
 * @author CZP
 * @parameter
 */
public class QuestionDao {

	public Question getQuestion(String questionId) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		Question question = new Question();
		session.beginTransaction();
		question = (Question) session.get(Question.class, Integer.parseInt(questionId));

		session.getTransaction().commit();
		return question;
	}

	// 传入试卷的Id，判断是否有该Id的问题
	public boolean isExistByPaperId(String paperId) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from Question as q where q.paper.id ='" + paperId + "'";
		Query query = session.createQuery(hql);
		List<Question> questionList = (List<Question>) query.list();
		session.getTransaction().commit();
		if (questionList.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public int questionListCount(Question question) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) as total from t_question as q ");
		if (question != null) {
			if (StringUtil.isNotEmpty(question.getSubject())) {
				hql.append(" and q.subject like '%" + question.getSubject() + "%'");
			}
		}
		Query query = session.createSQLQuery(hql.toString().replaceFirst("and", "where"));
		// 只对SQL语句 有效！！！
		int total = ((BigInteger) query.uniqueResult()).intValue();
		session.getTransaction().commit();
		return total;
	}

	public void questionUpdate(Question question) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		session.merge(question);

		session.getTransaction().commit();
	}

	public void questionDelete(Question question) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		session.delete(question);

		session.getTransaction().commit();
	}

	public List<Question> questionList(Question question, PageBean pageBean) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		List<Question> questionList = new ArrayList<>();
		session.beginTransaction();
		StringBuffer hql = new StringBuffer();
		hql.append("from Question as q ");
		if (question != null) {
			if (StringUtil.isNotEmpty(question.getSubject())) {
				hql.append(" and q.subject like '%" + question.getSubject() + "%'");
			}
		}
		Query query = session.createQuery(hql.toString().replaceFirst("and", "where"));
		if (pageBean != null) {
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getPageSize());
		}
		questionList = (List<Question>) query.list();
		session.getTransaction().commit();
		return questionList;
	}

}
