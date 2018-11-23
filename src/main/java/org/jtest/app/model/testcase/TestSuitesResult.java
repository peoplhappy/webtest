package org.jtest.app.model.testcase;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 测试集执行结果
 * 
 * @author pengchen
 *
 */
@Entity
@Table(name = "tbl_testsuiteresult")
public class TestSuitesResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1921269871969481929L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = false,name="name")
	private String name;// 测试集结果名称
	@Column(nullable = false, unique = false,name="projectid")
	private String projectId;// 属于的项目id
	@Column(nullable = true, unique = false,name="starttime")
	private String starttime;// 开始时间
	@Column(nullable = true, unique = false,name="endtime")
	private String endtime;// 结束时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
