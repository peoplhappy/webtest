package org.jtest.app.model.testcase;


import java.io.Serializable;
import java.util.List;

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
@Table(name = "tbl_testsuite")
public class TestSuite implements Serializable {

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
	private String testsuiteName;// 测试集名称
	@Column(nullable = true, unique = false, name = "description")
	private String description;// 测试集说明
	public String getTestsuiteName() {
		return testsuiteName;
	}

	public void setTestsuiteName(String testsuiteName) {
		this.testsuiteName = testsuiteName;
	}

	public String getTestcaseIds() {
		return testcaseIds;
	}

	public void setTestcaseIds(String testcaseIds) {
		this.testcaseIds = testcaseIds;
	}

	@Column(nullable = true, unique = false, name = "testcaseIds")
	private String testcaseIds;//测试集包含的测试用列,以List方式存储，转为gson存入
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
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
