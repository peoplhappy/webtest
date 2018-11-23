package org.jtest.app.model.asserts;

public class AssertModel {
	private String ExpectValue = "";
	private String actualValue = "";
	private String assertResult;
	private String assertMsg = "";

	public String getExpectValue() {
		return ExpectValue;
	}

	public void setExpectValue(String expectValue) {
		ExpectValue = expectValue;
	}

	public String getActualValue() {
		return actualValue;
	}

	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}

	public String isAssertResult() {
		return assertResult;
	}

	public void setAssertResult(String assertResult) {
		this.assertResult = assertResult;
	}

	public String getAssertMsg() {
		return assertMsg;
	}

	public void setAssertMsg(String assertMsg) {
		this.assertMsg = assertMsg;
	}

}
