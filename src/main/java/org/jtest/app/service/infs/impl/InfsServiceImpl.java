package org.jtest.app.service.infs.impl;

import java.util.List;

import org.jtest.app.dao.infs.InfsDao;
import org.jtest.app.model.infs.InfsInfo;
import org.jtest.app.service.infs.InfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfsServiceImpl implements InfsService{
	@Autowired
	private InfsDao infsdao;
	@Override
	public List<InfsInfo> findInfsList(String projectId) {
		// TODO Auto-generated method stub
		List<InfsInfo> infolist=infsdao.findByprojectId(projectId);
		return infolist;
	}
	@Override
	public InfsInfo updateInfs(InfsInfo info) {
		// TODO Auto-generated method stub
		
		return infsdao.save(info);
	}
	@Override
	public boolean deleteInfs(InfsInfo info) {
		// TODO Auto-generated method stub
		infsdao.delete(info);
		return true;
	}
	@Override
	public InfsInfo findInfs(String projectId, String infsname) {
		// TODO Auto-generated method stub
		InfsInfo info=new InfsInfo();
		List<InfsInfo> infolist=infsdao.findByprojectId(projectId);
		for(int i=0;i<infolist.size();i++){
			if(infolist.get(i).getInfsname().equalsIgnoreCase(infsname)){
				info=infolist.get(i);
				break;
			}
		}
		return info;
	}

}
