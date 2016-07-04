package Dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import Model.PageBean;
import Model.Student;
import Util.HibernateUtil;
import Util.StringUtil;

/**
 * @date 2016年3月14日 StudentDao.java
 * @author CZP
 * @parameter
 */
public class StudentDao {

	// 注意 此处 的参数 不再是 Connection 因为可以获取session
	public Student login(Student student) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		// 开始事务
		session.beginTransaction();
		// HQL语句查询
		Query query = session.createQuery("from Student as s where s.id=:id and s.password=:password ");
		// 设置查询参数
		query.setString("id", student.getId());
		query.setString("password", student.getPassword());
		// 执行语句
		Student resultStudent = (Student) query.uniqueResult();
		
		// 提交事务
		session.getTransaction().commit();

		return resultStudent;
	}

	public List<Student> getStudentList(Student student, PageBean pageBean) {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		List<Student> studentList = new ArrayList<>();
		StringBuffer hql = new StringBuffer();
		hql.append("from Student as s");
		if (StringUtil.isNotEmpty(student.getId())) {
			hql.append(" and  s.id like '%" + student.getId() + "%'");
		}
		if (StringUtil.isNotEmpty(student.getName())) {
			hql.append(" and s.name like '%" + student.getName() + "%'");
		}
		Query query = session.createQuery(hql.toString().replaceFirst("and", "where"));
		if (pageBean != null) {
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getPageSize());
		}
		studentList = (List<Student>) query.list();
		session.getTransaction().commit();
		return studentList;
	}

	public void studentDelete(Student student) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		session.delete(student);
		session.getTransaction().commit();
	}

	/**
	 * merge()方法详解 如果session中存在相同持久化标识(identifier)的实例，用用户给出的对象的状态覆盖旧有的持久实例
	 * 如果session没有相应的持久实例，则尝试从数据库中加载，或创建新的持久化实例 最后返回该持久实例
	 * 用户给出的这个对象没有被关联到session上，它依旧是脱管的
	 * 
	 * @param student
	 * @return
	 */

	public int getStudentCount(Student student) {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as total from t_student s ");
		if (StringUtil.isNotEmpty(student.getId())) {
			sql.append(" and  s.id like '%" + student.getId() + "%'");
		}
		if (StringUtil.isNotEmpty(student.getName())) {
			sql.append(" and s.name like '%" + student.getName() + "%'");
		}
		// SQL查询
		Query query = session.createSQLQuery(sql.toString().replaceFirst("and", "where"));
		// 注意数据类型转换
		int num = ((BigInteger) query.uniqueResult()).intValue();
		session.getTransaction().commit();
		return num;
	}

	public Student getStduentById(String studentId) throws Exception {
		Student student = new Student();
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();
		// get()方法 第一个参数是 获取对象的类的class，第二个是主键值
		student = (Student) session.get(Student.class, studentId);
		session.getTransaction().commit();
		return student;
	}

	public void saveStudent(Student student) throws Exception {
		Session session = HibernateUtil.getSessionFaactory().getCurrentSession();
		session.beginTransaction();

		session.merge(student);
		session.getTransaction().commit();
	}

}
