package Dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import Model.Paper;
import Util.HibernateUtil;

/**
 * @date 2016年3月14日 PaperDao.java
 * @author CZP
 * @parameter
 */
public class PaperDao {

	public List<Paper> getPaperList() throws Exception {
		List<Paper> paperList = new ArrayList<Paper>();
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();

		session.beginTransaction();
		Query query = session.createQuery("from Paper");
		paperList = (List<Paper>) query.list();
		session.getTransaction().commit();
		return paperList;
	}

	public void paperDelete(Paper paper) {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		session.delete(paper);
		session.getTransaction().commit();
	}

	public void paperUpdate(Paper paper) {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		session.merge(paper);

		session.getTransaction().commit();
	}

	// 随机获取一套试卷
	public Paper getPaper(String paperId) throws Exception {
		Paper paper = new Paper();
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		paper = (Paper) session.get(Paper.class, Integer.parseInt(paperId));

		session.getTransaction().commit();
		return paper;
	}

}
