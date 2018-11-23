package org.jtest.app.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
/**
 * 日志记录
 * @author pengchen
 *
 */
public class CustomLogger {
	private Logger logger = LoggerFactory.getLogger("testLogger");
	private String name;
    public CustomLogger(String name){
    	this.name=name;
    	//this.logger=LoggerFactory.getLogger(name);
    }
    /**
     * 打印info日志
     * @param data
     */
	public void logInfo(String data){
		
		 //System.out.println("info:"+name+"_"+MDC.get("logFileName")+" content:"+data+" threadname:"+Thread.currentThread().getName());  
		 
		 logger.info(data);		
		
	}
	/**
	 * 打印错误日志，不带throwable
	 * @param data
	 */
	public void logError(String data){
		 System.out.println("error:"+name+"_"+MDC.get("logFileName")+" content:"+data+" threadname:"+Thread.currentThread().getName());  
		 logger.error(data);		 
		 
	}
	/**
	 * 打印错误日志，带throwable
	 * @param data
	 */
	
	public void logError(String data,Throwable e){
		logger.error(data, e);
	}
	/**
	 * 打印警告日志
	 * @param data
	 */
	public void logWarn(String data){
		logger.warn(data);
	}
	/**
	 * 打印debug日志
	 * @param data
	 */
	
	public void logDebug(String data){
		logger.debug(data);
	}
	
}
