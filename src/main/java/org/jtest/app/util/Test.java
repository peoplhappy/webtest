package org.jtest.app.util;

import java.util.HashMap;
import java.util.Map;

import org.jtest.app.model.requests.RequestParamter;

import com.google.gson.Gson;

public class Test {
   
	public static void main(String[] args){
//		String str = "{\"jsondata\":{\"domain\":\"test.qmth.com.cn\",\"accountType\":\"COMMON_LOGIN_NAME\",\"accountValue\":\"pengchen02\",\"password\":\"123\"}}";
//		Gson gson=new Gson();
//		RequestParamter param=gson.fromJson(str, RequestParamter.class);
//		System.out.println(param.getJsondata().toString());
//		
		Map<String,String> keymap=new HashMap<String,String>();
		System.out.println(keymap.get("1"));
	}
}
