package org.jtest.app.model.requests;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

/*
 * 用以存儲request參數
 * 
 * paramter :通過url發送的參數列表
 * jsondata: 通過json發送
 * filedata: 上傳文件，不要添加文件頭
 * data: 键值对形式的参数,对应basicnamepair
 */
public class RequestParamter {

	private Map<String, Object> paramterdata = new HashMap<String, Object>();

	private JsonObject jsondata=null;

	private Map<String, FileInfo> filedata = new HashMap<String, FileInfo>();

	private Map<String, Object> data = new HashMap<String, Object>();

	public Map<String, Object> getParamterdata() {
		return paramterdata;
	}

	public void setParamterdata(Map<String, Object> paramterdata) {
		this.paramterdata = paramterdata;
	}

	public Map<String, FileInfo> getFiledata() {
		return filedata;
	}

	public void setFiledata(Map<String, FileInfo> filedata) {
		this.filedata = filedata;
	}

	public JsonObject getJsondata() {
		return jsondata;
	}

	public void setJsondata(JsonObject jsondata) {
		this.jsondata = jsondata;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
