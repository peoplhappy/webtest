package org.jtest.app.controller.testcase;

import java.util.ArrayList;
import java.util.List;

import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.model.testcase.TestCaseItem;
import org.jtest.app.model.testcase.TestSuite;
import org.jtest.app.responsebody.ResponseBody;
import org.jtest.app.service.testcase.TestCaseService;
import org.jtest.app.service.testcase.TestSuiteService;
import org.jtest.app.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class TestSuiteController {
     @Autowired
     private TestSuiteService testsuiteService;
     @Autowired
     private TestCaseService caseservice;
     private Gson gson=JsonUtil.gson;
     
     @PostMapping("/testsuite/createtestsuite")
     public TestSuite createTestSuite(TestSuite suite){
    	 return testsuiteService.createTestSuite(suite);
     }
     
     
     @GetMapping("/testsuite/gettestsuite")
     public TestSuite getTestSuites(@RequestParam(value = "testsuiteId") String testsuiteId){
    	 return testsuiteService.findById(testsuiteId);
     }
     
     
     @PostMapping("/testsuite/gettestcases")
     public List<TestCase> getTestCase(TestSuite suite){
    	 List<TestCase> testcaselist=testsuiteService.findTestCase(suite.getProjectId(), suite.getTestcaseIds());
    	 return testcaselist;
     }
     @PostMapping("/testsuite/addtestcase")
     public List<TestCase> addTestCase(TestSuite suit){
    	 TestSuite suites=testsuiteService.createTestSuite(suit);
    	 List<TestCase> testcaselist=testsuiteService.findTestCase(suites.getProjectId(), suites.getTestcaseIds());
    	 return testcaselist;
     }
     
     @PostMapping("/testsuite/getprojecttestcase")
     public List<TestCase> getProjectTestCase(TestSuite suit){
    	 List<TestCase> testcaselist=caseservice.findTestCases(suit.getProjectId());
    	 //过滤已添加的用列
    	 List<String> contentlist=gson.fromJson(suit.getTestcaseIds(), new TypeToken<List<String>>(){}.getType());
    	 List<TestCase> newlist=new ArrayList<TestCase>();
    	 for(TestCase testcase:testcaselist){
    		 if(!contentlist.contains(String.valueOf(testcase.getId()))){
    			 newlist.add(testcase);
    		 }
    	 }
    	 return newlist;
     }
}
