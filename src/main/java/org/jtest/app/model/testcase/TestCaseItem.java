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
 * 记录testcase 每个接口对象信息类
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "tbl_testcaseitem")
public class TestCaseItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8535081952666722717L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = false, name = "testcaseid")
	private String testcaseId;// 对应的testcase的id
	@Column(nullable = false, unique = false, name = "callbackname")
	private String callbackname;// 需要执行的接口name
	@Column(nullable = true, unique = false, name = "presetparameter")
	private String presetparameter;// 对应接口的预置参数
	@Column(nullable = true, unique = false, name = "assertlist")
	private String assertlist;// 对应接口的验证点列表
	@Column(nullable = true, unique = false, name = "type")
	private String type;// 执行的类型，接口或是其他

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestcaseId() {
		return testcaseId;
	}

	public void setTestcaseId(String testcaseId) {
		this.testcaseId = testcaseId;
	}

	public String getCallbackname() {
		return callbackname;
	}

	public void setCallbackname(String callbackname) {
		this.callbackname = callbackname;
	}

	public String getPresetparameter() {
		return presetparameter;
	}

	public void setPresetparameter(String presetparameter) {
		this.presetparameter = presetparameter;
	}

	public String getAssertlist() {
		return assertlist;
	}

	public void setAssertlist(String assertlist) {
		this.assertlist = assertlist;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
