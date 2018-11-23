package org.jtest.app.controller.protocal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtest.app.controller.BaseController;
import org.jtest.app.dao.protocal.ProtocalDao;

import org.jtest.app.model.infs.InfsResponse;
import org.jtest.app.model.protocal.ProtocalInfo;
import org.jtest.app.model.protocal.ProtocalResponse;
import org.jtest.app.service.protocal.ProtocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class ProtocalController extends BaseController {
	private Gson gson=new Gson();
	@Autowired
	private ProtocalService service;
	
	/**
	 * 查询子节点列表
	 * @param parentid
	 * @return
	 */
	@GetMapping("/protocal/getProtocalAll")
    public ProtocalResponse getProtocalInfoAll(){
    	//ItemModel model=itemdao.findByitemType("ROOT");
    	//ItemModel model2=itemdao.findByid(2L);
    	List<ProtocalInfo> protocallist=service.findAll();
    	ProtocalResponse result=new ProtocalResponse();
    	result.setRows(protocallist);
    	result.setTotal(String.valueOf(protocallist.size()));
    	System.out.println("获取数据"+gson.toJson(result,ProtocalResponse.class));
		return result;
    }
	
	@PostMapping("/protocal/submitprotocal")
	public ProtocalResponse sumitProtocal(ProtocalInfo info){
		ProtocalResponse result=new ProtocalResponse();
		List<ProtocalInfo> protocallist=new ArrayList<ProtocalInfo>();
		ProtocalInfo protocalinfos=service.updateProtocal(info);
		protocallist.add(protocalinfos);
		result.setRows(protocallist);
		return result;
		
	}
	
	
	@DeleteMapping("/protocal/deleteprotocals")
	public ProtocalResponse deleteInfs(@RequestBody ProtocalInfo info){
		ProtocalResponse response=new ProtocalResponse();
		if(info.getId()<=0){
			response.setResult(false);
			return response;
		}
		service.deleteProtocal(info);
		response.setResult(true);
		return response;
		
	}
	
}
