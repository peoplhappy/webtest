package org.jtest.app.dao.infs;

import java.util.List;
import java.util.Optional;

import org.jtest.app.model.infs.InfsInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfsDao extends JpaRepository<InfsInfo, Long>{
     public List<InfsInfo> findByprojectId(String projectId);

}
