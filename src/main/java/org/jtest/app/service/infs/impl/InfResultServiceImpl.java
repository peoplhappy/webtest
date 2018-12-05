package org.jtest.app.service.infs.impl;


import java.util.List;

import org.jtest.app.dao.infs.InfsResultDao;
import org.jtest.app.model.infs.InfsResult;
import org.jtest.app.service.infs.InfsResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfResultServiceImpl implements InfsResultService{

	@Autowired
	private InfsResultDao infsdao;
	@Override
	public InfsResult saveResult(InfsResult result) {
		// TODO Auto-generated method stub
		return infsdao.save(result);
	}
	@Override
	public List<InfsResult> findInterfaceResultList(String testcaseresultid) {
		// TODO Auto-generated method stub
		return infsdao.findBytestCaseResultId(testcaseresultid);
	}

	

}
