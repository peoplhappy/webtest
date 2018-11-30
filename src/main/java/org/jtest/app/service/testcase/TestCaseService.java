package org.jtest.app.service.testcase;

import java.util.List;

import org.jtest.app.model.item.ItemType;
import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.model.testcase.TestCaseItem;

public interface TestCaseService {
     public List<TestCaseItem> findTestCaseItem(String testcaseId);
     public TestCase findTestCase(String projectId,String testcaseId);
     public TestCaseItem findItem(String itemid);
     public TestCase updateTestcase(TestCase testcase);
     public TestCaseItem updateTestcaseItem(TestCaseItem item);
     public boolean deleteTestCase(TestCase testcase);
     public boolean deleteTestCaseItem(TestCaseItem item);
     public List<TestCase> findTestCases(String projectId);
     
	
}
