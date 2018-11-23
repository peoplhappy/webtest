package org.jtest.app.dao.testcase;


import java.util.List;

import org.jtest.app.model.testcase.TestCaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseItemDao extends JpaRepository<TestCaseItem, Long>{
    public List<TestCaseItem> findBytestcaseId(String testcaseId);
    public TestCaseItem findByid(Long id);
}
