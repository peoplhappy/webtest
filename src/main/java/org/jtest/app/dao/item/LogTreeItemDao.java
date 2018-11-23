package org.jtest.app.dao.item;

import java.util.List;

import org.jtest.app.model.item.LogItemType;
import org.jtest.app.model.item.LogTreeItem;
import org.jtest.app.model.item.ProjectTreeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LogTreeItemDao extends JpaRepository<LogTreeItem, Long>{
	public List<LogTreeItem> findByitemType(LogItemType itemType);
	public LogTreeItem findByid(Long id);
	public List<LogTreeItem> findByparentid(String parentid);
}
