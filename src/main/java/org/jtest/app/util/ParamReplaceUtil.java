package org.jtest.app.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jtest.app.dao.config.ConfigDao;
import org.jtest.app.model.param.MethodIndex;
import org.jtest.app.runtime.RunTimeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


/**
 * 替换
 * 
 * @author pwx322979
 *
 */
public class ParamReplaceUtil {
	private static final String matchStr = "\\$\\{([^\\$|\\})]+)\\}";
	private static final String matchMethod = "\\$(\\w+)\\(([^\\$]+)\\)";
	private static final String matchJson = "(?![a-zA-Z0-9,])[\\[|\\{].*[\\]|\\}]";
	private static final Pattern parampattern = Pattern.compile(matchStr);
	private static final Pattern methodpattern = Pattern.compile(matchMethod);
	private static final Pattern jsonpattern = Pattern.compile(matchJson);
	private static final String matchquote = "\"\"(?!,)\\S+\"\"";
	private static final Pattern quotepattern = Pattern.compile(matchquote);
	private static final String matchreplacedParam = "<map-([a-zA-Z]+)-([a-zA-Z_]+)>";
	private static final Pattern replacedParamPattern = Pattern.compile(matchreplacedParam);
    
	@Autowired
	private static ConfigDao configdao;
	/*
	 * $sum($sum(${config:username},${config:username}),$sum(${config:username},
	 * ${config:username}))
	 * 
	 * @param replaceStr
	 * 
	 * @return
	 */
	public static String Replace(String replaceStr) {
		
		String replaced = null;
		try {
			replaced = ReplaceParam(replaceStr);
			// 匹配api
			boolean apiexist = true;
			while (apiexist) {
				Matcher matcher = methodpattern.matcher(replaced);
				int id = 1;
				String str = replaced;
				while (matcher.find()) {
					String keystr = matcher.group(2);
					String methodname = matcher.group(1);
					MethodIndex index = getParam(matcher.group(0), '(', ')');
					keystr = matcher.group(0).substring(index.getStartindex(), index.getEndindex());
					// String replacedStr=ReplaceStr(keystr);
					Object result = doMethod(methodname, keystr);
					str = str.replace(matcher.group(0).substring(0, index.getEndindex() + 1), result.toString());
					id++;
				}
				if (id == 1) {
					apiexist = false;
				}
				replaced = str;
			}

			replaced = ReplacedStrReplaced(replaced);

		} catch (Exception e) {
			replaced = null;// 发生错误后赋值为
		}
		return replaced;
	}

	/**
	 * 按照(和)组队个数来去除
	 * 
	 * @param method
	 * @return
	 */
	public static MethodIndex getParam(String method, char begin, char end) {

		List<Integer> startindexs = new ArrayList<Integer>();
		List<Integer> endindexs = new ArrayList<Integer>();
		char[] array = method.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if (array[i] == begin) {
				startindexs.add(i);

			} else {
				if (array[i] == end) {
					endindexs.add(i);
				}
			}
			if (startindexs.size() != 0) {
				if (startindexs.size() == endindexs.size()) {
					break;
				}
			}

		}
		return new MethodIndex(startindexs.get(0) + 1, endindexs.get(endindexs.size() - 1));
	}

	public static Object doMethod(String methodname, String param) {
		Object result = null;
		Class clazz = StringApiUtil.class;

		// 避免切割json字符串,如果有两个json字符串时会匹配出问题
		// 修改匹配两个json字符串时bug
		System.out.println(param);
		List<String> jsonstrlist = new ArrayList<String>();
		// 匹配json
		boolean jsonexist = true;
		while (jsonexist) {
			Matcher matcher = jsonpattern.matcher(param);
			int id = 0;
			String str = param;
			while (matcher.find()) {
				String jsonstr = matcher.group(0);
				MethodIndex index = null;
				String str1 = null;
				if (jsonstr.startsWith("[")) {
					index = getParam(jsonstr, '[', ']');
					str1 = "[" + matcher.group(0).substring(index.getStartindex(), index.getEndindex()) + "]";
				} else if (jsonstr.startsWith("{")) {
					index = getParam(jsonstr, '{', '}');
					str1 = "{" + matcher.group(0).substring(index.getStartindex(), index.getEndindex()) + "}";
				}
				jsonstrlist.add(str1);
				str = str.replace(str1, id + "json");
				id++;
			}
			if (id == 0) {
				jsonexist = false;
			}
			param = str;
		}

		int paramindex = -1;
		String[] paramstr = null;
		if (param.contains("\",\"")) {
			paramstr = param.split("\",\"");
			paramindex = param.split("\",\"").length;
			// 去掉外层引号，避免引号带来问题
			for (int i = 0; i < paramindex; i++) {
				paramstr[i] = paramstr[i].replace("\"", "");
			}
		} else {
			paramstr = param.split(",");
			paramindex = param.split(",").length;
			// 去掉外层引号，避免引号带来问题
			for (int i = 0; i < paramindex; i++) {
				paramstr[i] = paramstr[i].replace("\"", "");
			}
		}
		Class[] clss = new Class[paramindex];
		Object[] objs = new Object[paramindex];
		for (int i = 0; i < paramindex; i++) {
			clss[i] = Object.class;
			Matcher matcher = replacedParamPattern.matcher(paramstr[i]);
			if (matcher.find()) {
				String valuepath = matcher.group(1);
				String valuekey = matcher.group(2);
				objs[i] = getvalue(valuepath, valuekey);
			} else {		
				if (paramstr[i].matches("(\\d+)json")) {
					String index = paramstr[i].replace("json", "");
					objs[i] = jsonstrlist.get(Integer.valueOf(index));
				}else{
					objs[i] = paramstr[i];
				}
				

			}

		}
		try {
			Method method = clazz.getMethod(methodname, clss);
			result = method.invoke(null, objs);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 替换函数
	 * 
	 * @param replaceStr
	 * @return
	 */
	public static String ReplaceParam(String replaceStr) {

		Matcher matcher = parampattern.matcher(replaceStr);
		String result = replaceStr;
		while (matcher.find()) {
			String keystr = matcher.group(1);
			String replacedStr = ReplaceStr(keystr);
			if (replacedStr != null) {
				result = result.replace(matcher.group(0), replacedStr);
			} else {
				result = result.replace(matcher.group(0), "null");
			}

		}

		return result;

	}

	public static String ReplaceStr(String keystr) {
		String[] keys = keystr.split("@");
		String getvaluepath = keys[0];
		String valuekey = keys[1];
		return "<map" + "-" + getvaluepath + "-" + valuekey+">";
	}

	public static String ReplacedStrReplaced(String replaceStr) {
		Matcher matcher = replacedParamPattern.matcher(replaceStr);
		String result = replaceStr;
		while (matcher.find()) {
			String valuepath = matcher.group(1);
			String valuekey = matcher.group(2);
			String value = getvalue(valuepath, valuekey);
			if (value != null) {
				result = result.replace(matcher.group(0), value);
			} else {
				result = result.replace(matcher.group(0), "null");
			}

		}

		return result;
	}

	public static String getvalue(String getvaluepath, String valuekey) {
		String threadName=RunTimeConfig.getCurrentThreadName();	
		Map<String,String> paramsMap=RunTimeConfig.configmap.get(threadName);
		if (getvaluepath.equalsIgnoreCase("config")) {
			/**
			 * 从配置文件中取值
			 */
			return  paramsMap.get(valuekey);
		} else if (getvaluepath.equalsIgnoreCase("cell")) {
			/**
			 * 从map中取值
			 */
			if (paramsMap.containsKey(valuekey)) {
				return paramsMap.get(valuekey).toString();
			} else {
				return null;
			}

		}
		return null;
	}

	public static String removeQuote(String str) {
		Matcher matcher = quotepattern.matcher(str);
		while (matcher.find()) {
			String str1 = matcher.group(0);
			String str2 = str1.substring(1, str1.length() - 1);
			str = str.replace(str1, str2);
		}
		return str;
	}

	public static void main(String[] args) {
	//	TestRunTime.param.put("", "");
		String str = Replace("");
		System.out.println(str);

	}
}
