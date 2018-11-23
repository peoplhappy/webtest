package org.jtest.app.model.infs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 接口执行结果
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name="tbl_infsresult")
public class InfsResult implements Serializable {
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getInfsName() {
		return infsName;
	}

	public void setInfsName(String infsName) {
		this.infsName = infsName;
	}

	public String getTestCaseResultId() {
		return testCaseResultId;
	}

	public void setTestCaseResultId(String testCaseResultId) {
		this.testCaseResultId = testCaseResultId;
	}

	public String getAssertresultlist() {
		return assertresultlist;
	}

	public void setAssertresultlist(String assertresultlist) {
		this.assertresultlist = assertresultlist;
	}

	public String getExcuteResult() {
		return excuteResult;
	}

	public void setExcuteResult(String excuteResult) {
		this.excuteResult = excuteResult;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 3080331812570323407L;
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(nullable = false, unique = false,name="infsname")
	private String infsName;
	@Column(nullable = false, unique = false,name="testcaseresultid")
	private String testCaseResultId;
	@Column(nullable = true, unique = false,name="assertresultlist")
	private String assertresultlist;
	@Column(nullable = true, unique = false,name="excuteresult")
	private String excuteResult;
	@Column(nullable = true, unique = false,name="starttime")
	private String starttime;
	@Column(nullable = true, unique = false,name="endtime")
	private String endtime;

}
