package org.jtest.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jtest.app.log.LogManager;
import org.jtest.app.model.asserts.AssertModel;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 用以执行字符串中存在的函数，在此处添加api
 * @author pengchen
 *
 */

public class StringApiUtil {
	private static Gson gson = JsonUtil.gson;
	private static List<String> uniqueList=new ArrayList<String>();
    /**
     * 通过uri获取json中的字符串的值
     * @param data
     * @param uri
     * @return
     * @throws Exception 
     */
	public static String getValue(Object data, Object uri) throws Exception {
		return PythonUtil.getJsonValue(data.toString(), uri.toString());
	}
	/**
     * 通过uri获取html中的字符串的值
     * @param data
     * @param uri
     * @return
     * @throws Exception 
     */
	public static String getHtmlValue(Object data, Object uri) throws Exception {
		//HttpRevResult str=gson.fromJson(data.toString(), new TypeToken<HttpRevResult>(){}.getType());
		return PythonUtil.getHtmlValue(data.toString(), uri.toString());
	}
    /**
     * 字符串拼接
     * @param t1
     * @param t2
     * @return
     */
	public static String sum(Object t1, Object t2) {
		return t1.toString() + t2.toString();
	}
    /**
     * 字符串替换，返回替换后字符串
     * @param origin
     * @param keystr
     * @param replacedstr
     * @return
     */
	public static String replace(Object origin, Object keystr, Object replacedstr) {
		if(replacedstr.equals("\"\"")){
			return origin.toString().replace(keystr.toString(), "");
		}
		return origin.toString().replace(keystr.toString(), replacedstr.toString());
	}
    /**
     * 分割字符串，返回分割后对应index的字符串
     * @param origin
     * @param splitkey
     * @param idx
     * @return
     */
	public static String splitStr(Object origin, Object splitkey, Object idx) {
		return origin.toString().split(splitkey.toString())[Integer.valueOf(idx.toString())];
	}

	/**
	 * 加入验证点
	 * 
	 * @param message
	 * @param expect
	 * @param actual
	 */
	public static String assertsEqual(Object message, Object expect, Object actual) {
		AssertModel model = new AssertModel();
		model.setActualValue(actual.toString());
		model.setExpectValue(expect.toString());
		model.setAssertMsg(message.toString());
		if (expect.toString().equalsIgnoreCase(actual.toString())) {
			model.setAssertResult("OK");
		//	LogManager.getLogger().LogInfo("验证点验证成功"+gson.toJson(model, AssertModel.class));
            
		} else {
			model.setAssertResult("POK");
		//	LogManager.getLogger().LogError("验证点验证失败"+gson.toJson(model, AssertModel.class));
		}
        
		return gson.toJson(model, AssertModel.class);
	}
	public static boolean isEmpty(Object str){
		if(StringUtils.isEmpty(str)){
			return true;
		}else{
			if(str.equals("[]")){
				return true;
			}else if(str.equals("{}")){
				return true;
			}
			return false;
		}
	}
	/**
	 * 截取字符串
	 * @param origin 待截取的字符串
	 * @param beginIndex  起始位置
	 * @param endIndex  结束位置
	 * @return 返回截取后的字符串
	 */
	public static String subStr(Object origin, Object beginIndex, Object endIndex) {
		int bi = Integer.valueOf(beginIndex.toString());
		int ei = Integer.valueOf(endIndex.toString());
		return origin.toString().substring(bi, ei);
	}
	/**
	 * 返回产生的unique字符串
	 * @param index 返回的位置
	 * @param newflag 若为newflag则创建新的字符串
	 * @return
	 */
	public static String unique(Object index,Object newflag){
		boolean flag=Boolean.valueOf(newflag.toString());
		if(flag){
			//产生新的uuid并返回
			uniqueList.add(String.valueOf(System.currentTimeMillis()));
			return uniqueList.get(uniqueList.size()-1);	
		}else{
			int indexId=Integer.valueOf(index.toString());
			if(uniqueList.size()>indexId){
				return uniqueList.get(indexId);
			}else{
				uniqueList.add(String.valueOf(System.currentTimeMillis()));
				return uniqueList.get(uniqueList.size()-1);				
			}
		}
		
	}
	/**
	 * 判断是否包含某个字符串
	 * @param srcstr
	 * @param str
	 * @return
	 */
	public static boolean contains(Object srcstr,Object str){
		if(srcstr.toString().contains(str.toString())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取距今为止几天的时间字符串
	 * @param day
	 * @return
	 */
	public static String getBeforeAfterTime(Object day){
		
		//return TimeUtil.getLastedTime(day);
		return null;
	}
	
	
	/**
	 * 获取正则匹配值
	 */
	public static String getComplieValue(Object origindata,Object pattern,Object Index){
		String origin=origindata.toString();
		String patterns=pattern.toString().replace("\\\\", "\\");
		int indexs=Integer.valueOf(Index.toString());
		Pattern pat=Pattern.compile(patterns);
		Matcher matcher=pat.matcher(origin);
	    if(matcher.find()){
	    	return matcher.group(indexs);
	    }else{
	    	return "";
	    }
	}
	
}
