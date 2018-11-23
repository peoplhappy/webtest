package org.jtest.app.model.param;

/**
 * 用来定位方法的起始和结束位置
 * @author pengchen
 *
 */
public class MethodIndex {
	private int startindex;

	public int getStartindex() {
		return startindex;
	}

	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}

	public int getEndindex() {
		return endindex;
	}

	public void setEndindex(int endindex) {
		this.endindex = endindex;
	}

	private int endindex;

	public MethodIndex(int startindex, int endindex) {
		this.startindex = startindex;
		this.endindex = endindex;
	}
}
