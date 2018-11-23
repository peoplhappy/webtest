package org.jtest.app.model.infs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 存储interface的相关信息
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "tbl_interface")
public class InfsInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6644110753287918586L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // interface id
	@Column(nullable = false, unique = true, name = "infsname")
	private String infsname;
	@Column(nullable = false, unique = false, name = "headerinfo")
	private String headerinfo;
	@Column(nullable = true, unique = false, name = "paramterinfo")
	private String paramterinfo;
	@Column(nullable = true, unique = false, name = "description")
	private String description;
	@Column(nullable = false, unique = false, name = "protocol")
	private String protocol;
	@Column(nullable = false, unique = false, name = "sendtype")
	private String sendType;
	@Column(nullable = false, unique = false, name = "projectid")
	private String projectId;
	@Column(nullable = false, unique = false, name = "infsurl")
	private String infsurl;
	@Column(nullable = true, unique = false, name = "returntype")
	private String returnType; // 返回类型
	@Column(nullable = true, unique = false, name = "returnurl")
	private String returnUrl;
	@Column(nullable = true, unique = false, name = "successcondition")
	private String successcondition;
	@Column(nullable = true, unique = false, name = "excutetype")
	private String excuteType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInfsname() {
		return infsname;
	}

	public void setInfsname(String infsname) {
		this.infsname = infsname;
	}

	public String getHeaderinfo() {
		return headerinfo;
	}

	public void setHeaderinfo(String headerinfo) {
		this.headerinfo = headerinfo;
	}

	public String getParamterinfo() {
		return paramterinfo;
	}

	public void setParamterinfo(String paramterinfo) {
		this.paramterinfo = paramterinfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getInfsurl() {
		return infsurl;
	}

	public void setInfsurl(String infsurl) {
		this.infsurl = infsurl;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getSuccesscondition() {
		return successcondition;
	}

	public void setSuccesscondition(String successcondition) {
		this.successcondition = successcondition;
	}

	public String getExcuteType() {
		return excuteType;
	}

	public void setExcuteType(String excuteType) {
		this.excuteType = excuteType;
	}
}
