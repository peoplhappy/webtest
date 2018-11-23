package org.jtest.app.dao.testcase;



import java.util.List;

import org.jtest.app.model.testcase.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestCaseDao extends JpaRepository<TestCase, Long>{
	 @Query(value = "select t from TestCase t where t.projectId = :projectid and t.id= :id")
	 public TestCase findTestCase(@Param("projectid") String projectid,@Param("id") Long id);
}
