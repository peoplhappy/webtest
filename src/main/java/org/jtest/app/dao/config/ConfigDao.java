package org.jtest.app.dao.config;

import java.util.List;

import org.jtest.app.model.config.ConfigInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigDao extends JpaRepository<ConfigInfo, Long>{
     public List<ConfigInfo> findByprojectId(String projectId);
     public ConfigInfo findByKeynameAndProjectId(String projectId,String keyname);
}
