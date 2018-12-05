package org.jtest.app.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.jtest.app.log.LogManager;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.util.StringUtils;
import java.util.Properties;

public class PythonUtil {
	private static String path = System.getProperty("user.dir") + File.separator + "getvalue.py";
	private static String temppath = System.getProperty("user.dir") + File.separator + "temp.txt";

	// 提取json值，jsondata通过文件传递，使用完毕后删除
	public static String getJsonValue(String jsondata, String valuepath) throws Exception {
		FileUtil.writeToFile(jsondata, temppath, false);
		// String data=jsondata.replace("\"", "\"\"\"");
		String result = null;
		Runtime runtime = Runtime.getRuntime();
		String cmd = "python " + path + " " + "json" + " " + "\"" + temppath + "\"" + " " + valuepath;
	    LogManager.getLogger().logInfo("Run cmd is"+cmd);
		Process pr;
		try {
			pr = runtime.exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), Charset.forName("GBK")));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				result = line;
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result == null) {
			throw new Exception("get Result failed please check");
		}
		return result;

	}

	public static String getHtmlValue(String htmldata, String valuepath) throws Exception {
		FileUtil.writeToFile(htmldata, temppath, false);
		// String data=jsondata.replace("\"", "\"\"\"");
		String result = null;
		Runtime runtime = Runtime.getRuntime();
		String cmd = "python " + path + " " + "html" + " " + "\"" + temppath + "\"" + " " + "\"" + valuepath + "\"";
		// LogManager.getLogger().LogInfo("Run cmd is "+cmd);
		Process pr;
		try {
			pr = runtime.exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), Charset.forName("GBK")));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				result = line;
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result == null) {
			throw new Exception("get Result failed please check");
		}
		return result;
	}

	public static void main(String[] args){
		//用jython尝试
		
		try{
			Properties props=new Properties();
			props.put("python.home", "C:\\Python27");
			props.put("python.console.encoding","UTF-8");// Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0. 
			props.put("python.security.respectJavaAccessibility", "false");//don't respect java accessibility, so that we can access protected members on subclasses  
			props.put("python.import.site","false");
			Properties preprops=System.getProperties();
			PythonInterpreter.initialize(preprops,props,new String[0]);
			PythonInterpreter interpreter = new PythonInterpreter(); 
			interpreter.exec("import sys");
	        interpreter.exec("sys.path.append('C:\\Python27\\Lib')");//jython自己的
	        interpreter.exec("sys.path.append('C:\\Python27\\Lib\\site-packages')");//jython 加载脚本的Python的jar包

	        interpreter.execfile("E:\\考试云平台\\10_接口自动化\\api.py");  
			PyFunction func = (PyFunction)interpreter.get("httpGet",PyFunction.class); 
			PyObject pyobj = func.__call__();                                                                    
			System.out.println(pyobj.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//System.out.println(result);
	}
}
