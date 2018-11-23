package org.jtest.app.model.testcase;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 存储testcase的相关信息
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "tbl_testcase")
public class TestCase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2223808417427786754L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = false, name = "projectid")
	private String projectId;// 从属的projectId
	@Column(nullable = false, unique = false, name = "name")
	private String testcaseName;// 用例名称
	@Column(nullable = true, unique = false, name = "description")
	private String description;// 用例说明

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTestcaseName() {
		return testcaseName;
	}

	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
