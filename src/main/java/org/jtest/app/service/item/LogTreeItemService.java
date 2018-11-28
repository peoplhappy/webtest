package org.jtest.app.service.item;

import java.util.List;

import org.jtest.app.model.item.LogItemType;
import org.jtest.app.model.item.LogTreeItem;

public interface LogTreeItemService {
	public List<LogTreeItem> findChildLst(String parentId);

	public LogTreeItem findItem(String itemid);

	public LogTreeItem createItem(LogTreeItem item);
	
	public List<LogTreeItem> findItemByProjectIdandItemType(LogItemType type,String projectId);
	
	public List<LogTreeItem> findItemType(LogItemType itemType);
	
	public LogTreeItem saveResult(LogTreeItem item);
}
