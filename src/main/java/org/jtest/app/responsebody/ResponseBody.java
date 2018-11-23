package org.jtest.app.responsebody;

import java.util.ArrayList;
import java.util.List;

public class ResponseBody<T> {
	private List<T> Rows = new ArrayList<T>();
	private String Total;
	private boolean result;

	public List<T> getRows() {
		return Rows;
	}

	public void setRows(List<T> rows) {
		Rows = rows;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotal(String total) {
		Total = total;
	}
}
