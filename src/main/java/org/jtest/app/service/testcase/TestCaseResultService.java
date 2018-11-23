package org.jtest.app.service.testcase;

import java.util.List;

import org.jtest.app.model.infs.InfsResult;
import org.jtest.app.model.testcase.TestCaseResult;




public interface TestCaseResultService {
 
     public TestCaseResult findTestCaseResult(String testcaseresultId);
     public List<InfsResult> findInfsResultList(String testcaseresultId);
     public TestCaseResult saveResult(TestCaseResult result);
     
	
}
