package org.jtest.app.service.protocal.impl;

import java.util.List;

import org.jtest.app.dao.protocal.ProtocalDao;
import org.jtest.app.model.protocal.ProtocalInfo;
import org.jtest.app.service.protocal.ProtocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProtocalServiceImpl implements ProtocalService{

	@Autowired
	private ProtocalDao protocaldao;
	@Override
	public ProtocalInfo updateProtocal(ProtocalInfo info) {
		// TODO Auto-generated method stub
		return protocaldao.save(info);
	}

	@Override
	public boolean deleteProtocal(ProtocalInfo info) {
		// TODO Auto-generated method stub
		protocaldao.delete(info);
		return true;
	}

	@Override
	public List<ProtocalInfo> findAll() {
		// TODO Auto-generated method stub
		
		return protocaldao.findAll();
	}

	@Override
	public ProtocalInfo findProtocal(String protocalname, String sendType) {
		// TODO Auto-generated method stub
		List<ProtocalInfo> protocallist=protocaldao.findAll();
		ProtocalInfo info=new ProtocalInfo();
		for(int i=0;i<protocallist.size();i++){
			ProtocalInfo pro=protocallist.get(i);
			if(pro.getProtocalname().equalsIgnoreCase(protocalname)&&pro.getSendType().equals(sendType)){
				info=pro;
				break;
			}
		}
		return info;
	}

}
