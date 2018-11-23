package org.jtest.app.service.protocal;

import java.util.List;

import org.jtest.app.model.protocal.ProtocalInfo;

public interface ProtocalService {
   public ProtocalInfo updateProtocal(ProtocalInfo info);
   public boolean deleteProtocal(ProtocalInfo info);
   public List<ProtocalInfo> findAll();
   public ProtocalInfo findProtocal(String protocalname,String sendType);
}
