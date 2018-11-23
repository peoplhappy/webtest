package org.jtest.app.service.item;

import java.util.List;

import org.jtest.app.model.item.ProjectTreeItem;
import org.springframework.stereotype.Service;


public interface ProjectTreeItemService {
    public List<ProjectTreeItem> findChildLst(String parentId);
    public ProjectTreeItem findItem(String itemid);
    public boolean updateItem(ProjectTreeItem item);
    public boolean deleteItem(ProjectTreeItem item);
    public boolean updateItemPosition(ProjectTreeItem item);
    public ProjectTreeItem createItem(ProjectTreeItem item);
}
