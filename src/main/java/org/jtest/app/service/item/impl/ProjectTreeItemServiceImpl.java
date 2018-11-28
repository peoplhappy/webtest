package org.jtest.app.service.item.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.jtest.app.dao.item.ProjectTreeItemDao;
import org.jtest.app.model.item.ItemType;
import org.jtest.app.model.item.LogItemType;
import org.jtest.app.model.item.LogTreeItem;
import org.jtest.app.model.item.ProjectTreeItem;
import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.service.item.LogTreeItemService;
import org.jtest.app.service.item.ProjectTreeItemService;
import org.jtest.app.service.testcase.TestCaseService;
import org.jtest.app.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("itemservice")
public class ProjectTreeItemServiceImpl implements ProjectTreeItemService{

	@Autowired
	private ProjectTreeItemDao itemdao;
	
	@Autowired
	private TestCaseService testcaseservice;
	
	@Autowired
	private LogTreeItemService itemservice;
	
	@Override
	public List<ProjectTreeItem> findChildLst(String parentId) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(parentId)){
			return null;
		}else{
			List<ProjectTreeItem> model=itemdao.findByparentid(parentId);
			return model;
		}
		
	}

	@Override
	public ProjectTreeItem findItem(String itemid) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(itemid)){
			return null;
		}else{
			ProjectTreeItem model=itemdao.findByid(Long.valueOf(itemid));
			return model;
		}
		
	}

	@Override
	public boolean updateItem(ProjectTreeItem item) {
		// TODO Auto-generated method stub
		itemdao.save(item);	
		return true;
	}

	@Override
	public boolean deleteItem(ProjectTreeItem item) {
		// TODO Auto-generated method stub
		List<ProjectTreeItem> childnodelist=itemdao.findByparentid(String.valueOf(item.getId()));
		for(int i=0;i<childnodelist.size();i++){
			deleteItem(childnodelist.get(i));
		}
		itemdao.delete(item);
		return true;
	}

	@Override
	public boolean updateItemPosition(ProjectTreeItem item) {
		// TODO Auto-generated method stub
		ProjectTreeItem olditem=itemdao.findByid(item.getId());
		//获取老父节点
		ProjectTreeItem oldparentItem=itemdao.findByid(Long.valueOf(olditem.getParentid()));
		//oldparentItem.setChildlist(removechildLst(oldparentItem,item.getId()));
		itemdao.save(oldparentItem);
		//获取新父节点
		ProjectTreeItem newparentItem=itemdao.findByid(Long.valueOf(item.getParentid()));
		//同步修改childLst
//		String childLst=newparentItem.getChildlist();
//		if(StringUtils.isEmpty(childLst)){
//			childLst=String.valueOf(item.getId());
//		}else{
//			childLst=childLst+","+item.getId();
//		}
//		
//		newparentItem.setChildlist(childLst);
//		itemdao.save(newparentItem);		
		return true;
	}
    public String removechildLst(ProjectTreeItem parent,Long childid){
    	//String[] childLst=parent.getChildlist().split(",");
		//List<String> childLists=new ArrayList<String>(Arrays.asList(childLst)) ;
//		int idx=childLists.indexOf(String.valueOf(childid));
//		if(idx>-1){
//			childLists.remove(idx);
//		}
//		String[] newchildLst=childLists.toArray(new String[childLists.size()]);
//		return StringUtil.join(newchildLst, ",");
		return "";
    }
	@Override
	public ProjectTreeItem createItem(ProjectTreeItem item) {
		// TODO Auto-generated method stub
		ProjectTreeItem newitem=null;
		if(item.getItemType().equals(ItemType.PROJECT)){
			newitem=createProject(item);
		}else{
			newitem=createNormalItem(item);
		}
		return newitem;
	}
	
	
	/**
	 * 优先创建projectitem，在projectitem下在创建接口配置文件和配置文件
	 * @return
	 */
	public ProjectTreeItem createProject(ProjectTreeItem item){
		ProjectTreeItem newProjectItem=createNormalItem(item);
		/**
		 * 创建接口配置文件
		 */
		ProjectTreeItem newInterfaceConfigItem=new ProjectTreeItem();
		newInterfaceConfigItem.setItemType(ItemType.INFSCONFIGFILE);
		newInterfaceConfigItem.setParentid(String.valueOf(newProjectItem.getId()));
		newInterfaceConfigItem.setProjectId(String.valueOf(newProjectItem.getId()));
		newInterfaceConfigItem.setText("接口配置文件");
		newInterfaceConfigItem.setUrl("../jtest/html/Infsview.html?projectId="+newProjectItem.getId());
		newInterfaceConfigItem=createNormalItem(newInterfaceConfigItem);
		
		
		/**
		 * 创建配置文件
		 */
		ProjectTreeItem newConfigItem=new ProjectTreeItem();
		newConfigItem.setItemType(ItemType.CONFIGFILE);
		newConfigItem.setParentid(String.valueOf(newProjectItem.getId()));
		newConfigItem.setProjectId(String.valueOf(newProjectItem.getId()));
		newConfigItem.setText("项目基础配置文件");
		newConfigItem.setUrl("../jtest/html/configview.html?projectId="+newProjectItem.getId());
		newConfigItem=createNormalItem(newConfigItem);
        
		newProjectItem.setHaschildren(true);
		itemdao.save(newProjectItem);
		
		//存储到LogTreeItem下面去
		
		LogTreeItem logtreeitem=new LogTreeItem();
		logtreeitem.setHaschildren(false);
		logtreeitem.setItemType(LogItemType.PROJECT);
		logtreeitem.setProjectId(String.valueOf(newProjectItem.getId()));
		logtreeitem.setText(newProjectItem.getText());
		itemservice.saveResult(logtreeitem);
		return newProjectItem;
		
		
	}
	
	
	public ProjectTreeItem createNormalItem(ProjectTreeItem item){
		String url=null;

		ProjectTreeItem parent=itemdao.findByid(Long.valueOf(item.getParentid()));     
		if(item.getItemType().equals(ItemType.TESTCASE)){
			//新建testcase
			TestCase newtestcase=new TestCase();
			newtestcase.setDescription("");
			newtestcase.setProjectId(item.getProjectId());
			newtestcase.setTestcaseName(item.getText());
			newtestcase=testcaseservice.updateTestcase(newtestcase);
			url="../jtest/html/testcaseview.html?projectId="+item.getProjectId()+"&testcaseId="+newtestcase.getId();
			item.setUrl(url);
		}else if(item.getItemType().equals(ItemType.TESTSUITS)){
			//新建testsuites
			url="../jtest/html/testsuitesview.html?testcaseId=";
			item.setUrl(url);
		}
		
		ProjectTreeItem newitem=itemdao.save(item);
		parent.setHaschildren(true);
		itemdao.save(parent);
		return newitem;
	}

}
