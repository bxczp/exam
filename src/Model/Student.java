package Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * @date 2016年3月14日 User.java
 * @author CZP
 * @parameter
 */
@Entity
@Table(name = "t_student")
public class Student {

	// 此处的id是 学生的准考证
	private String id = "";
	private String name = "";
	private String password;
	private String sex;
	private String prefession;
	private String cardNo;
	// 判断 是 普通用户 还是 管理员
	// 不写入 数据库
	private String flag = "2";

	// 双向关联
	private List<Exam> examList = new ArrayList<Exam>();

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 40)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 20)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 5)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(length = 40)
	public String getPrefession() {
		return prefession;
	}

	public void setPrefession(String prefession) {
		this.prefession = prefession;
	}

	// 不写入数据库
	@Transient
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(length = 50)
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@OneToMany(mappedBy = "student")
	// 级联删除
	@Cascade(CascadeType.DELETE)
	public List<Exam> getExamList() {
		return examList;
	}

	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}

}
