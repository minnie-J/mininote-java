package com.mininote.model;


public class OneLineText {
	
	private int lineNum;
	private String oneLineStr;
	private String creationDate;
	
	public OneLineText() {}
	
	public OneLineText(String oneLineStr, String creationDate) {
		this.oneLineStr = oneLineStr;
		this.creationDate = creationDate;
	}
	
	public OneLineText(int lineNum, String oneLineStr) {
		this.lineNum = lineNum;
		this.oneLineStr = oneLineStr;
	}
	
	public OneLineText(int lineNum, String oneLineStr, String creationtDate) {
		this.lineNum = lineNum;
		this.oneLineStr = oneLineStr;
		this.creationDate = creationtDate;
	}

	public String getOneLineStr() {
		return oneLineStr;
	}

	public void setOneLineStr(String oneLineStr) {
		this.oneLineStr = oneLineStr;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public int getLineNum() {
		return lineNum;
	}

	
}
