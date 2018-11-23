package org.jtest.app.controller.infs;

import java.util.ArrayList;
import java.util.List;

import org.jtest.app.controller.BaseController;
import org.jtest.app.dao.infs.InfsDao;
import org.jtest.app.model.infs.InfsInfo;
import org.jtest.app.model.infs.InfsResponse;
import org.jtest.app.service.infs.InfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InfsController extends BaseController{
	@Autowired
	private InfsService infsservice;
	
	@GetMapping("/infs/getAllinfs")
	public InfsResponse getInfsAll(@RequestParam(value = "projectId") String projectId){
		List<InfsInfo> infslist=infsservice.findInfsList(projectId);
		InfsResponse response=new InfsResponse();
		response.setRows(infslist);
		response.setTotal(String.valueOf(infslist.size()));
		return response;
	}
	
	@PostMapping("/infs/submitinfs")
	public InfsResponse sumitinfs(InfsInfo info){
		InfsResponse result=new InfsResponse();
		List<InfsInfo> InfsInfolist=new ArrayList<InfsInfo>();
		InfsInfo infsInfos=infsservice.updateInfs(info);
		InfsInfolist.add(infsInfos);
		result.setRows(InfsInfolist);
		return result;
		
	}
	
	@DeleteMapping("/infs/deleteInfs")
	public InfsResponse deleteInfs(@RequestBody InfsInfo info){
		InfsResponse response=new InfsResponse();
		if(info.getId()<=0){
			response.setResult(false);
			return response;
		}
		infsservice.deleteInfs(info);
		response.setResult(true);
		return response;
		
	}
}
