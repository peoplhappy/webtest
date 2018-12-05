package org.jtest.app.controller.testcase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.jtest.app.config.BeanContext;
import org.jtest.app.controller.BaseController;
import org.jtest.app.model.infs.InfsResult;
import org.jtest.app.model.testcase.TestCaseResult;
import org.jtest.app.service.infs.InfsResultService;
import org.jtest.app.service.testcase.TestCaseResultService;
import org.jtest.app.service.testcase.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCaseResultController extends BaseController {
	@Autowired
	private TestCaseResultService testcaseResultservice;

	@Autowired
	private InfsResultService infres;

	@GetMapping("/result/testcaseresult")
	public TestCaseResult getTestResult(@RequestParam(value = "testcaseresultId") String testcaseresultid) {

		return testcaseResultservice.findTestCaseResult(testcaseresultid);

	}

	@GetMapping("/result/getlogcontent")
	public String getLogContent(@RequestParam(value = "logfileurl") String logfileurl) {
		
		StringBuilder sb = new StringBuilder();
		try {
			File file=new File(System.getProperty("user.dir")+logfileurl);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            
			
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line+"<Br />");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
		

	}
	@GetMapping("/result/getinfsresult")
	public List<InfsResult> getInfstestResult(@RequestParam(value = "testcaseresultId") String testcaseresultId){	
		return testcaseResultservice.findInfsResultList(testcaseresultId);
	}

}
