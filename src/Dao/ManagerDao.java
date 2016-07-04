package Dao;

import org.hibernate.Query;
import org.hibernate.Session;

import Model.Manager;
import Util.HibernateUtil;

/**
 * @date 2016年3月15日 ManagerDao.java
 * @author CZP
 * @parameter
 */
public class ManagerDao {

	public Manager login(Manager manager) {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Manager as m where m.userName = :userName  and password = :password");
		query.setString("userName", manager.getUserName());
		query.setString("password", manager.getPassword());
		Manager m = (Manager) query.uniqueResult();
		session.getTransaction().commit();
		return m;
	}

}
