package org.jtest.app.service.testcase.impl;

import java.util.List;

import org.jtest.app.dao.testcase.TestCaseResultDao;
import org.jtest.app.model.infs.InfsResult;
import org.jtest.app.model.testcase.TestCaseResult;
import org.jtest.app.service.testcase.TestCaseResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestCaseResultServiceImpl implements TestCaseResultService{
    @Autowired
    
    private TestCaseResultDao resdao;
	@Override
	public TestCaseResult findTestCaseResult(String testcaseresultId) {
		// TODO Auto-generated method stub
		long id=Long.valueOf(testcaseresultId);
		return resdao.findById(id);
	}

	@Override
	public List<InfsResult> findInfsResultList(String testcaseresultId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestCaseResult saveResult(TestCaseResult result) {
		// TODO Auto-generated method stub
		return resdao.save(result);
	}

}
