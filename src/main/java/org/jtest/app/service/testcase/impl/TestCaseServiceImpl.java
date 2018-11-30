package org.jtest.app.service.testcase.impl;

import java.util.List;

import org.jtest.app.dao.testcase.TestCaseDao;
import org.jtest.app.dao.testcase.TestCaseItemDao;
import org.jtest.app.model.item.ItemType;
import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.model.testcase.TestCaseItem;
import org.jtest.app.service.testcase.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestCaseServiceImpl implements TestCaseService{
    @Autowired
    private TestCaseDao testcasedao;
    @Autowired
    private TestCaseItemDao testcaseitemdao;
	@Override
	public List<TestCaseItem> findTestCaseItem(String testcaseId) {
		// TODO Auto-generated method stub
		List<TestCaseItem> itemLst=testcaseitemdao.findBytestcaseId(testcaseId);
		return itemLst;
	}
	@Override
	public TestCase findTestCase(String projectId, String testcaseId) {
		// TODO Auto-generated method stub
		TestCase testcae=testcasedao.findTestCase(projectId, Long.valueOf(testcaseId));
		return testcae;
	}
	@Override
	public TestCaseItem findItem(String itemid) {
		// TODO Auto-generated method stub
		TestCaseItem item=testcaseitemdao.findByid(Long.valueOf(itemid));
		return item;
	}
	@Override
	public TestCase updateTestcase(TestCase testcase) {
		// TODO Auto-generated method stub
		TestCase newtestcase=testcasedao.save(testcase);
		return newtestcase;
	}
	@Override
	public TestCaseItem updateTestcaseItem(TestCaseItem item) {
		// TODO Auto-generated method stub
		TestCaseItem newitem=testcaseitemdao.save(item);
		return newitem;
	}
	@Override
	public boolean deleteTestCase(TestCase testcase) {
		// TODO Auto-generated method stub
		testcasedao.delete(testcase);
		return true;
	}
	@Override
	public boolean deleteTestCaseItem(TestCaseItem item) {
		// TODO Auto-generated method stub
		testcaseitemdao.delete(item);
		return true;
	}
	/**
	 * 找出该projectid下的所有testcase
	 */
	@Override
	public List<TestCase> findTestCases(String projectId) {
		// TODO Auto-generated method stub
		return testcasedao.findByprojectId(projectId);
	}
}
