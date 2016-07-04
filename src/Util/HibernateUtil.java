package Util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @date 2016年3月14日 aaUtil.java
 * @author CZP
 * @parameter
 */
public class HibernateUtil {
	// 单列模式
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		// 实例化配置文件
		Configuration cfg = new Configuration().configure();
		// 登记服务
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties()).build();
		return cfg.buildSessionFactory(serviceRegistry);
	}

	public static SessionFactory getSessionFaactory() {
		return sessionFactory;
	}

	public static void closeSessionFaactory() {
		sessionFactory.close();
	}

	public static void main(String[] args) {
		HibernateUtil.getSessionFaactory();
	}
}
