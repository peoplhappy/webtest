package org.jtest.app.model.requests;

public class ResponseResult {
	private int code; // 存储返回码

	public String getErrmessage() {
		return errmessage;
	}

	public void setErrmessage(String errmessage) {
		this.errmessage = errmessage;
	}

	private String errmessage; // 存储错误
	private boolean excuteResult;// 存储执行是否成功

	public boolean isExcuteResult() {
		return excuteResult;
	}

	public void setExcuteResult(boolean excuteResult) {
		this.excuteResult = excuteResult;
	}

	private Object result;// 存储执行结果

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
