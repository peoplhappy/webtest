package org.jtest.app.model.threadlocal;

import java.util.HashMap;
import java.util.Map;

import org.jtest.app.log.CustomLogger;
import org.jtest.app.runtime.RunTimeConfig;

/**
 * 存储线程的本地变量
 * @author Administrator
 *
 */
public class ThreadLocalParam {
     private Map<String,Object> parammap=new HashMap<String,Object>();
     
     public Object getvalue(String key){
     	return parammap.get(key);
     }
     
     public void setvalue(String key,String value){
    	 parammap.put(key, value);
     }
}
