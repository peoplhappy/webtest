package org.jtest.app.log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import ch.qos.logback.core.rolling.RollingFileAppender;

import org.jtest.app.runtime.RunTimeConfig;
import org.slf4j.Logger;



/**
 * 日志记录管理类
 * 
 * @author pengchen
 *
 */
public class LogManager {
	private static Map<String,CustomLogger> logmap=new HashMap<String,CustomLogger>();
	

	public LogManager() {
             
	}

	/**
	 * 获取日志句柄
	 * @return
	 */
	public synchronized static CustomLogger getLogger() {
		String threadName=Thread.currentThread().getName();
		if(logmap.containsKey(threadName)){
			return logmap.get(threadName);
		}
		CustomLogger log = new CustomLogger(threadName);
		logmap.put(threadName, log);
		return log;
	}
    public static List<String> getLoggerName(){
    	List<String> namelist=new ArrayList<String>();
    	
    	Set<String> keyset=logmap.keySet();
    	for(String name:keyset){
    		namelist.add(name);
    	}
    	return namelist;
    	
    }
	

	

//	public static void main(String[] args) throws IOException {
//		LogManager.getLogger().LogInfo("test1", "start to action");
//		String data = "{\"loginName\": \"test5\",\"password\": \"123\",\"userid\": \"\",\"appid\": \"\",\"timestamp\":{\"mapname\":\"1235\",\"tth\":\"23545\"},\"token\": \"\"}";
//		String valuepath = "timestamp/mapname";
//
//		String result = pytest.getJsonValue(data, valuepath);
//		LogManager.getLogger().LogInfo("test1", "Result is" + result);
//		LogManager.getLogger().LogInfo("Result is" + result);
//	}
}
