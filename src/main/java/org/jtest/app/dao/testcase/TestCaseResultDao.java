package org.jtest.app.dao.testcase;


import org.jtest.app.model.testcase.TestCaseResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseResultDao extends JpaRepository<TestCaseResult, Long>{
       public TestCaseResult findById(long id);
}
