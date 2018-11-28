package org.jtest.app.controller.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jtest.app.controller.BaseController;
import org.jtest.app.model.item.ItemType;
import org.jtest.app.model.item.ProjectTreeItem;
import org.jtest.app.service.item.ProjectTreeItemService;
import org.jtest.app.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * 获取项目树中项目
 * @author pengchen
 *
 */
@RestController
@RequestMapping("/tree")
public class TreeItemController extends BaseController{
	private Gson gson=new Gson();
	@Autowired
	private ProjectTreeItemService itemservice;

	
	/**
	 * 查询子节点列表
	 * @param parentid
	 * @return
	 */
	@GetMapping("/getItemlst")
    public List<ProjectTreeItem> getItemList(@RequestParam(value = "parentid", defaultValue = "1") String parentid){
    	//ItemModel model=itemservice.findByitemType("ROOT");
    	//ItemModel model2=itemservice.findByid(2L);
		HttpServletRequest request=this.getRequest();
    	List<ProjectTreeItem> model=itemservice.findChildLst(parentid);
    	System.out.println("获取数据"+gson.toJson(model,new TypeToken<List<ProjectTreeItem>>(){}.getType()));
		return model;
    }
	/**
	 * 通过id查询item
	 * @param id
	 * @return
	 */
	@GetMapping("/getItem")
	public ProjectTreeItem getItem(@RequestParam(value = "id") String id){
		ProjectTreeItem model=itemservice.findItem(id);
		
		return model;
	}
	/**
	 * 创建item
	 * @param item
	 * @return
	 */
	@PostMapping("/createItem")
	public ProjectTreeItem createItem(ProjectTreeItem item){
		ProjectTreeItem newitem=itemservice.createItem(item);
		return newitem;
	}
	
	/**
	 * 删除item
	 * @param item
	 * @return
	 */
	@DeleteMapping("/deleteItem")
	public boolean deleteItem(@RequestBody ProjectTreeItem item){
		
		
		//更新父节点的子节点信息
		ProjectTreeItem parent=itemservice.findItem(item.getParentid());
		//删除节点和该节点下所有节点
		itemservice.deleteItem(item);
		//获取所有节点
		List<ProjectTreeItem> itemlist=itemservice.findChildLst(item.getParentid());
		if(itemlist==null||itemlist.size()==0){
			//更新父节点haschildren属性为false
			parent.setHaschildren(false);
			itemservice.updateItem(parent);
		}
        return true;
		
	}
	
	/**
	 * 修改item名称
	 * @param item
	 * @return
	 */
	@PutMapping("/modifyItem")
	public boolean modifyItemName(@RequestBody ProjectTreeItem item){
		itemservice.updateItem(item);	
		return true;
	}
	/**
	 * 修改item位置，只有folder和testcase，可以修改，其余禁止修改
	 * @param item
	 * @return
	 */
	@PutMapping("/modifyItemPos")
	public boolean modifyItemPosition(@RequestBody ProjectTreeItem item){
		//根据id获取老item
		//ItemModel olditem=itemservice.findItem(item.getId());
		//禁止跨project
		
		return true;
	}
	
	
}
