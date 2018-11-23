package org.jtest.app.dao.infs;

import java.util.List;
import org.jtest.app.model.infs.InfsResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfsResultDao extends JpaRepository<InfsResult, Long>{
     public List<InfsResult> findBytestCaseResultId(String TestCaseResultId);

}
