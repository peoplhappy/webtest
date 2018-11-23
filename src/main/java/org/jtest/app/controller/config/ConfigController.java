package org.jtest.app.controller.config;

import java.util.ArrayList;
import java.util.List;

import org.jtest.app.controller.BaseController;
import org.jtest.app.dao.config.ConfigDao;
import org.jtest.app.model.config.ConfigInfo;
import org.jtest.app.model.protocal.ProtocalInfo;
import org.jtest.app.model.protocal.ProtocalResponse;
import org.jtest.app.responsebody.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController extends BaseController {
	@Autowired
	private ConfigDao configdao;
	@GetMapping("/config/getConfigAll")
	public ResponseBody getAllConfigInfo(@RequestParam(value = "projectId") String projectId){
		ResponseBody result=new ResponseBody<ConfigInfo>();
		List<ConfigInfo> configlist=configdao.findByprojectId(projectId);
		result.setRows(configlist);
		result.setTotal(String.valueOf(configlist.size()));
		return result;
		
	}
	
	@PostMapping("/config/submit")
	public ResponseBody sumit(ConfigInfo info){
		ResponseBody result=new ResponseBody<ConfigInfo>();
		List<ConfigInfo> configInfolist=new ArrayList<ConfigInfo>();
		ConfigInfo configinfo=configdao.save(info);
		configInfolist.add(configinfo);
		result.setRows(configInfolist);
		return result;
		
	}
	
	
	@DeleteMapping("/config/deleteconfig")
	public ResponseBody deleteInfs(@RequestBody ConfigInfo info){
		ResponseBody response=new ResponseBody<ConfigInfo>();
		if(info.getId()<=0){
			response.setResult(false);
			return response;
		}
		configdao.delete(info);
		response.setResult(true);
		return response;
		
	}
}
