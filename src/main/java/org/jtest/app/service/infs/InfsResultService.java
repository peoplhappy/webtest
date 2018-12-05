package org.jtest.app.service.infs;

import java.util.List;

import org.jtest.app.model.infs.InfsResult;



/**
 * 接口执行结果存储
 * @author pengchen
 *
 */
public interface InfsResultService {
	public InfsResult saveResult(InfsResult result);
	public List<InfsResult> findInterfaceResultList(String testcaseresultid);
}
