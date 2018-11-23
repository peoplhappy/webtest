package org.jtest.app.service.infs;

import java.util.List;

import org.jtest.app.model.infs.InfsInfo;



public interface InfsService {
    public List<InfsInfo> findInfsList(String projectId);
    public InfsInfo updateInfs(InfsInfo info);
    public boolean deleteInfs(InfsInfo info);
    public InfsInfo findInfs(String projectId,String infsname);
}
