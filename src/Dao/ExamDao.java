package Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import Model.Exam;
import Model.PageBean;
import Util.HibernateUtil;
import Util.StringUtil;

/**
 * @date 2016年3月15日 ExamDao.java
 * @author CZP
 * @parameter
 */
public class ExamDao {

	public Exam getExam() throws Exception {
		Exam exam = new Exam();

		return exam;
	}

	public void saveExam(Exam exam) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		// merge 合并
		// merge方法 相当有update方法，但不会持久化给定的对象
		session.merge(exam);

		session.getTransaction().commit();
	}

	public List<Exam> getExamList(Exam exam, PageBean pageBean) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		List<Exam> examList = new ArrayList<>();
		StringBuffer hql = new StringBuffer();
		hql.append("from Exam e ");
		if (exam.getStudent() != null) {
			if (StringUtil.isNotEmpty(exam.getStudent().getId())) {
				hql.append(" and e.student.id = '" + exam.getStudent().getId() + "'");
			}
			if (StringUtil.isNotEmpty(exam.getStudent().getName())) {
				hql.append(" and e.student.name like '%" + exam.getStudent().getName() + "%'");
			}
		}
		Query query = session.createQuery(hql.toString().replaceFirst("and", "where"));
		if (pageBean != null) {
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getPageSize());
		}
		examList = (List<Exam>) query.list();
		session.getTransaction().commit();
		return examList;
	}

	public int getExamListCount(Exam exam) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) as total from t_exam e,t_student s where e.studentId=s.id");
		if (exam.getStudent() != null) {
			if (StringUtil.isNotEmpty(exam.getStudent().getId())) {
				hql.append(" and s.id = '" + exam.getStudent().getId() + "'");
			}
			if (StringUtil.isNotEmpty(exam.getStudent().getName())) {
				hql.append(" and s.name like '%" + exam.getStudent().getName() + "%'");
			}
		}
		Query query = session.createSQLQuery(hql.toString());
		int total = ((BigInteger) query.uniqueResult()).intValue();
		session.getTransaction().commit();
		return total;
	}

}
