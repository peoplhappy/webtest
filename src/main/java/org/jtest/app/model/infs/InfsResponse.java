package org.jtest.app.model.infs;

import java.util.ArrayList;
import java.util.List;

import org.jtest.app.model.protocal.ProtocalInfo;

public class InfsResponse {
	private List<InfsInfo> Rows = new ArrayList<InfsInfo>();

	public List<InfsInfo> getRows() {
		return Rows;
	}

	public void setRows(List<InfsInfo> rows) {
		Rows = rows;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotal(String total) {
		Total = total;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	private String Total;
	private boolean result;
}
