package com.mininote.note;

public interface SqlQuery {
	String URL = "jdbc:oracle:thin:@172.30.1.15:1521:XE";
	String USER = "scott";
	String PASSWORD = "tiger";
	
	String SQL_SELECT_ALL = 
			"select * from onelinenote "
			+ "order by creation_date";
	
	String SQL_SELECT_BY_DATE = 
			"select * from onelinenote "
			+ "where creation_date = ?";
	
	String SQL_INSERT = 
			"insert into onelinenote (oneline_str, creation_date) "
			+ "values (?, ?)";
	
	String SQL_UPDATE =
			"update onelinenote "
			+ "set oneline_str = ? "
			+ "where creation_date = ?";
	
	String SQL_DELETE = 
			"delete from onelinenote "
			+ "where creation_date = ?";
	
	String SQL_SELECT_ALL_NOTE = 
			"select * from notepad "
			+ "order by update_date desc";
	
	String SQL_INSERT_NOTE = 
			"insert into notepad (title, content, update_date) "
			+ "values (?, ?, ?)";
	
	String SQL_DELETE_NOTE = 
			"delete from notepad "
			+ "where note_num = ?";
}
