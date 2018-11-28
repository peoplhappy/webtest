package org.jtest.app.service.testcase;

import java.util.List;

import org.jtest.app.model.testcase.TestCase;
import org.jtest.app.model.testcase.TestSuite;

public interface TestSuiteService {
    public TestSuite findById(String id);
    public List<TestCase> findTestCase(String projectId,String testcaseIds);
}
