package org.jtest.app.controller.testcase;

import java.util.List;

import org.jtest.app.controller.BaseController;
import org.jtest.app.dao.testcase.TestCaseDao;
import org.jtest.app.dao.testcase.TestCaseItemDao;
import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.model.testcase.TestCaseItem;
import org.jtest.app.responsebody.ResponseBody;
import org.jtest.app.service.testcase.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestCaseController extends BaseController{
	@Autowired
	private TestCaseService testcaseservice;  
	/**
	 * 通过projectid和testcaseid，获取testcase
	 * 进而通过itemid，获取testcaseitem
	 */
	@GetMapping("/testcase/getTestCaseItem")
	public ResponseBody getTestCaseItem(@RequestParam(value = "projectId") String projectId,@RequestParam(value = "testcaseId") String testcaseId){
		TestCase testcase=testcaseservice.findTestCase(projectId, testcaseId);
		List<TestCaseItem> itemlst=testcaseservice.findTestCaseItem(String.valueOf(testcase.getId()));
		ResponseBody<TestCaseItem> result=new ResponseBody<TestCaseItem>();
		result.setRows(itemlst);
		result.setTotal(String.valueOf(itemlst.size()));
		
		return result;
	}
	@PostMapping("/testcase/createTestcase")
	public TestCase createTestCase(TestCase cases){
		TestCase newcase=testcaseservice.updateTestcase(cases);
		return newcase;
	}
	@PostMapping("/testcase/createTestcaseitem")
	public TestCaseItem createTestCase(TestCaseItem item){
		TestCaseItem newcase=testcaseservice.updateTestcaseItem(item);
		return newcase;
	}
	
	@DeleteMapping("/testcase/deleteTestcase")
	public boolean deleteTestCase(@RequestBody TestCase testcase){
		List<TestCaseItem> itemLst=testcaseservice.findTestCaseItem(String.valueOf(testcase.getId()));
		testcaseservice.deleteTestCase(testcase);
		for(int i=0;i<itemLst.size();i++){
			testcaseservice.deleteTestCaseItem(itemLst.get(i));
		}
		//同时删除下属所有testcaseitem
		return true;
	}
	
	@DeleteMapping("/testcase/deleteTestcaseItem")
	public boolean deleteTestCaseItem(@RequestBody TestCaseItem item){
		testcaseservice.deleteTestCaseItem(item);
		return true;
	}
	
	
	@GetMapping("/testcase/gettestcase")
	public TestCase getTestCase(@RequestParam(value = "projectId") String projectId,@RequestParam(value = "testcaseId") String testcaseId){
		TestCase testcase=testcaseservice.findTestCase(projectId, testcaseId);
		return testcase;
	}
	
	
}
