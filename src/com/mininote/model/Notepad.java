package com.mininote.model;

import java.io.Serializable;

public class Notepad implements Serializable {

	private String fileName;
	private String title;
	private String content;
	private String lastModiDate;
	
	public Notepad() {}

	public Notepad(String fileName, String title, String content, String lastModiDate) {
		this.fileName = fileName;
		this.title = title;
		this.content = content;
		this.lastModiDate = lastModiDate;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getLastModiDate() {
		return lastModiDate;
	}

	public void setLastModiDate(String lastModiDate) {
		this.lastModiDate = lastModiDate;
	}
	
	
} // end class Notepad
