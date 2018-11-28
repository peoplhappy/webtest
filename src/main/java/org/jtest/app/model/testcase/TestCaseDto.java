package org.jtest.app.model.testcase;

/**
 * 传输模型
 * 
 * @author pengchen
 *
 */
public class TestCaseDto {
	private String userId;
	private String projectId;
	private String testcaseId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTestcaseId() {
		return testcaseId;
	}

	public void setTestcaseId(String testcaseId) {
		this.testcaseId = testcaseId;
	}
}
