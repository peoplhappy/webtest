package org.jtest.app.model.protocal;

import java.util.ArrayList;
import java.util.List;

public class ProtocalResponse {
	private List<ProtocalInfo> Rows = new ArrayList<ProtocalInfo>();
	private String Total;
	private boolean result;

	public List<ProtocalInfo> getRows() {
		return Rows;
	}

	public void setRows(List<ProtocalInfo> rows) {
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
