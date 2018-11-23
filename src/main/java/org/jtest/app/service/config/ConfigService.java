package org.jtest.app.service.config;

import java.util.List;

import org.jtest.app.model.config.ConfigInfo;

public interface ConfigService {
	 public List<ConfigInfo> findByprojectId(String projectId);
     public ConfigInfo findByKeynameAndProjectId(String projectId,String keyname);
}
