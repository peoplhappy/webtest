package org.jtest.app.service.testcase.impl;

import java.util.ArrayList;
import java.util.List;

import org.jtest.app.dao.testcase.TestSuiteDao;
import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.model.testcase.TestSuite;
import org.jtest.app.service.testcase.TestCaseService;
import org.jtest.app.service.testcase.TestSuiteService;
import org.jtest.app.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class TestSuiteServiceImpl implements TestSuiteService {
    @Autowired
    private TestSuiteDao testsuitdao;
	
    @Autowired
    private TestCaseService testcaseservice;
    private Gson gson=JsonUtil.gson;
	@Override
	public TestSuite findById(String id) {
		// TODO Auto-generated method stub
		long testid=Long.valueOf(id);
		return testsuitdao.findById(testid);
	}

	@Override
	public List<TestCase> findTestCase(String projectId,String testcaseIds) {
		// TODO Auto-generated method stub
		List<String> testcaseIdList=gson.fromJson(testcaseIds, new TypeToken<List<String>>(){}.getType());
		List<TestCase> testcaseList=new ArrayList<TestCase>();
		for(String id:testcaseIdList){
			TestCase testcase=testcaseservice.findTestCase(projectId, id);
			testcaseList.add(testcase);
		}
		return testcaseList;
	}

	@Override
	public TestSuite createTestSuite(TestSuite suite) {
		// TODO Auto-generated method stub
		return testsuitdao.save(suite);
	}

}
