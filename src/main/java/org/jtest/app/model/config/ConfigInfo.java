package org.jtest.app.model.config;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_config")
public class ConfigInfo implements Serializable {

	private static final long serialVersionUID = -6106413054496217897L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	@Column(nullable = false, unique = false, name = "keyname")
	private String keyname;// key
	@Column(nullable = false, unique = false, name = "value")
	private String valuestr;// 变量的值
	@Column(nullable = false, unique = false, name = "projectid")
	private String projectId; // 归属项目id

	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

	public String getValuestr() {
		return valuestr;
	}

	public void setValuestr(String valuestr) {
		this.valuestr = valuestr;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
