package org.jtest.app.dao.testcase;

import org.jtest.app.model.testcase.TestSuite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSuiteDao extends JpaRepository<TestSuite, Long>{
     public TestSuite findById(long id);
}
