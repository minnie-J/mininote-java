package com.mininote.note;

import java.util.List;

import com.mininote.model.Notpad;
import com.mininote.model.OneLineText;

public interface MiniNoteDao {

	List<OneLineText> selectAll();
	
	OneLineText select(String oneLineStr);
	
	int insert(OneLineText text);
	
	int update(OneLineText text);
	
	int delete(String date);
	
	List<Notpad> selectAllNote();
	
	Notpad selectNote(Notpad note);
	
	int insertNote(Notpad note);
	
	int updateNote(Notpad note);
	
	int deleteNote(int noteNum);
	
	
}
