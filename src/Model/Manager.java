package Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * @date 2016年3月14日 Manager.java
 * @author CZP
 * @parameter
 */
// 注意注解的引入类
@Entity
@Table(name = "t_manager")
public class Manager {

	private int id;
	private String userName;
	private String password;
	// 权限名
	private String name;
	// 判断登陆的用户 是 普通用户 还是管理员
	// 不映射到数据库
	private String flag = "1";

	// 字段的映射 写在 getter 方法上
	@Id
	@GeneratedValue(generator = "_native")
	@GenericGenerator(name = "_native", strategy = "native")
	// @Column（默认使用 字段名 对应 列名）
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 不映射到数据库
	@Transient
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
