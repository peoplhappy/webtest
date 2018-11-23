package org.jtest.app.runtime;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author pengchen
 *
 */
public class RunTimeConfig {
	//通过进程名称进行加载参数和获取
    public static Map<String,Map<String,String>> configmap=new HashMap<String,Map<String,String>>();
    //保证线程安全
    public synchronized static String getCurrentThreadName(){
    	return Thread.currentThread().getName();
    }
    
    public static String currentLogPath;
}
