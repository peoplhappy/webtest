package org.jtest.app.service.config.impl;

import java.util.List;

import org.jtest.app.dao.config.ConfigDao;
import org.jtest.app.model.config.ConfigInfo;
import org.jtest.app.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配置文件读取
 * @author pengchen
 *
 */

@Service
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigDao configdao;

	@Override
	public List<ConfigInfo> findByprojectId(String projectId) {
		// TODO Auto-generated method stub
		return configdao.findByprojectId(projectId);
	}

	@Override
	public ConfigInfo findByKeynameAndProjectId(String projectId, String keyname) {
		// TODO Auto-generated method stub
		return configdao.findByKeynameAndProjectId(projectId, keyname);
	}
}
