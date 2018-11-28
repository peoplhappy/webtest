package org.jtest.app.service.item.impl;

import java.util.ArrayList;
import java.util.List;

import org.jtest.app.dao.item.LogTreeItemDao;
import org.jtest.app.model.item.ItemType;
import org.jtest.app.model.item.LogItemType;
import org.jtest.app.model.item.LogTreeItem;
import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.service.item.LogTreeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
public class LogTreeItemServiceImpl implements LogTreeItemService{
    
	@Autowired
	private LogTreeItemDao itemdao;
	@Override
	public List<LogTreeItem> findChildLst(String parentId) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(parentId)){
			return null;
		}else{
			List<LogTreeItem> model=itemdao.findByparentid(parentId);
			return model;
		}
	}

	@Override
	public LogTreeItem findItem(String itemid) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(itemid)){
			return null;
		}else{
			LogTreeItem model=itemdao.findByid(Long.valueOf(itemid));
			return model;
		}
	}

	@Override
	public LogTreeItem createItem(LogTreeItem item) {
		// TODO Auto-generated method stub
		return createNormalItem(item);
	}
	
	
	public LogTreeItem createNormalItem(LogTreeItem item){
		LogTreeItem parent=itemdao.findByid(Long.valueOf(item.getParentid()));     
		LogTreeItem newitem=itemdao.save(item);
		parent.setHaschildren(true);
		itemdao.save(parent);
		return newitem;
	}

	@Override
	public List<LogTreeItem> findItemByProjectIdandItemType(LogItemType type,String projectId ) {
		// TODO Auto-generated method stub
		List<LogTreeItem> targetList=new ArrayList<LogTreeItem>();
		List<LogTreeItem> srcList=itemdao.findByitemType(type);
		for(LogTreeItem item:srcList){
			if(item.getProjectId().equalsIgnoreCase(projectId)){
				targetList.add(item);
			}
		}
		
		return targetList;
	}

	@Override
	public List<LogTreeItem> findItemType(LogItemType itemType) {
		// TODO Auto-generated method stub
		return itemdao.findByitemType(itemType);
	}

	@Override
	public LogTreeItem saveResult(LogTreeItem item) {
		// TODO Auto-generated method stub
		return itemdao.save(item);
	}


}
