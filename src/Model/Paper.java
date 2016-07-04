package Model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @date 2016年3月14日 Paper.java
 * @author CZP
 * @parameter
 */

@Entity
@Table(name = "t_paper")
public class Paper {

	private Integer id;
	private String paperName;
	private Date joinDate;
	private Set<Question> questions = new HashSet<>();

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	// mappedBy对应的是Question类中的 paper 属性
	// 设置Fetch （EAGER）立即加载 即 获取paper对象后 立即加载其中所有的question
	@OneToMany(mappedBy = "paper", fetch = FetchType.EAGER)
	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	@Id
	@GeneratedValue(generator = "_native")
	@GenericGenerator(name = "_native", strategy = "native")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
