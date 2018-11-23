package org.jtest.app;

import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.thread.RunThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
//		RunThread th=new RunThread("111");
//		TestCase testcase=new TestCase();
//		testcase.setProjectId("31");
//		testcase.setId(4L);
//		testcase.setTestcaseName("用户登录");
//		th.runTestCase(testcase);
	}
}
