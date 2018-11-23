package org.jtest.app.model.testcase;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 存储用例执行结果
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "tbl_testcaseresult")
public class TestCaseResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8118558764183091787L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = false, name = "logfileurl")
	private String logfileurl;// 日志的存储路径
	@Column(nullable = true, unique = false, name = "starttime")
	private String starttime;// 开始时间
	@Column(nullable = true, unique = false, name = "endtime")
	private String endtime;// 结束时间
	@Column(nullable = false, unique = false, name = "excuteresult")
	private String excuteResult;// 用列执行结果
	@Column(nullable = true, unique = false, name = "testsuiteresult")
	private String testsuitesresultId; // testsuitsresultId,从属于那个testsuitesresult下的运行结果执行
	@Column(nullable = false, unique = false, name = "name")
	private String name;
	@Column(nullable = false, unique = false, name = "projectid")
	private String projectId;// 属于的项目id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogfileurl() {
		return logfileurl;
	}

	public void setLogfileurl(String logfileurl) {
		this.logfileurl = logfileurl;
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

	public String getExcuteResult() {
		return excuteResult;
	}

	public void setExcuteResult(String excuteResult) {
		this.excuteResult = excuteResult;
	}

	public String getTestsuitesresultId() {
		return testsuitesresultId;
	}

	public void setTestsuitesresultId(String testsuitesresultId) {
		this.testsuitesresultId = testsuitesresultId;
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

}
