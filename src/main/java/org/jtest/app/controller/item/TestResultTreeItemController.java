package org.jtest.app.controller.item;

import java.util.List;

import org.jtest.app.model.item.LogItemType;
import org.jtest.app.model.item.LogTreeItem;
import org.jtest.app.service.item.LogTreeItemService;
import org.jtest.app.service.item.ProjectTreeItemService;
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
 * 操作测试结果的tree
 * 
 * @author pengchen
 *
 */
@RestController
@RequestMapping("/testresult/tree")
public class TestResultTreeItemController {
	private Gson gson=new Gson();
	@Autowired
	private LogTreeItemService itemservice;

	
	/**
	 * 查询子节点列表
	 * @param parentid
	 * @return
	 */
	@GetMapping("/getItemlst")
    public List<LogTreeItem> getItemList(@RequestParam(value = "parentid", defaultValue = "1") String parentid){
    	//ItemModel model=itemservice.findByitemType("ROOT");
    	//ItemModel model2=itemservice.findByid(2L);
    	List<LogTreeItem> model=itemservice.findChildLst(parentid);
    	System.out.println("获取数据"+gson.toJson(model,new TypeToken<List<LogTreeItem>>(){}.getType()));
		return model;
    }
	/**
	 * 通过id查询item
	 * @param id
	 * @return
	 */
	@GetMapping("/getItem")
	public LogTreeItem getItem(@RequestParam(value = "id") String id){
		LogTreeItem model=itemservice.findItem(id);
		
		return model;
	}
	
	/**
	 * 通过id查询item
	 * @param id
	 * @return
	 */
	@GetMapping("/getItemByType")
	public List<LogTreeItem> getItemByType(@RequestParam(value = "itemType") LogItemType itemType){
		List<LogTreeItem> model=itemservice.findItemType(itemType);
		
		return model;
	}
	
}
