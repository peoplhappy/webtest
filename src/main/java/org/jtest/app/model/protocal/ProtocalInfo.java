package org.jtest.app.model.protocal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_protocal")
public class ProtocalInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6797443024619591093L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(nullable = false, unique = false, name = "protocalname")
	private String protocalname;//协议名称
	@Column(nullable = false, unique = false, name = "sendtype")
	private String sendType;//发送类型
	@Column(nullable = false, unique = false, name = "languagename")
	private String languageName;//语言，JAVA和PYTHON
	@Column(nullable = true, unique = false, name = "isthird3rd")
	private boolean third3rd;
	@Column(nullable = true, unique = false, name = "third3rdpackageurl")
	private String third3rdPackageUrl;
	@Column(nullable = false, unique = false, updatable = true, name = "methodname")
	private String methodName;
	@Column(nullable = false, unique = false, name = "classfileurl")
	private String classFileUrl;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getProtocalname() {
		return protocalname;
	}

	public void setProtocalname(String protocalname) {
		this.protocalname = protocalname;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public boolean isThird3rd() {
		return third3rd;
	}

	public void setThird3rd(boolean third3rd) {
		this.third3rd = third3rd;
	}

	public String getThird3rdPackageUrl() {
		return third3rdPackageUrl;
	}

	public void setThird3rdPackageUrl(String third3rdPackageUrl) {
		this.third3rdPackageUrl = third3rdPackageUrl;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassFileUrl() {
		return classFileUrl;
	}

	public void setClassFileUrl(String classFileUrl) {
		this.classFileUrl = classFileUrl;
	}
}
