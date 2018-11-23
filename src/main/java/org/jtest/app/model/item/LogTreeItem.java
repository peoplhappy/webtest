package org.jtest.app.model.item;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 日志结果存储节点
 * @author pengchen
 *
 */
@Entity
@Table(name = "tbl_logtreeitem")
public class LogTreeItem implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = true, unique = false, name = "parentId")
	private String parentid;
	@Column(nullable = true, name = "haschildren")
	private boolean haschildren;// 是否擁有孩子節點
	@Column(nullable = false, name = "itemtype")
	@Enumerated(EnumType.STRING)
	private LogItemType itemType;// 项目，文件夹，文件，测试用例，测试集
	@Column(nullable = true, name = "url")
	private String url; // 文件对应的url，根据item类型调用对应的方式
	@Column(nullable = true, name = "projectid")
	private String projectId;// Root为根目录
	@Column(nullable = true, name = "text")
	private String text;// 显示在树上的内容
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public boolean isHaschildren() {
		return haschildren;
	}

	public void setHaschildren(boolean haschildren) {
		this.haschildren = haschildren;
	}

	public LogItemType getItemType() {
		return itemType;
	}

	public void setItemType(LogItemType itemType) {
		this.itemType = itemType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
