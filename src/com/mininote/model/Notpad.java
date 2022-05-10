package com.mininote.model;

public class Notpad {

	private int noteNum;
	private String title;
	private String content;
	private String updateDate;
	
	public Notpad() {}
	
	public Notpad(String title, String content, String updateDate) {
		this.title = title;
		this.content = content;
		this.updateDate = updateDate;
	}
	
	public Notpad(int noteNum, String title, String content, String updateDate) {
		this.noteNum = noteNum;
		this.title = title;
		this.content = content;
		this.updateDate = updateDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getNoteNum() {
		return noteNum;
	}
	
	
}
