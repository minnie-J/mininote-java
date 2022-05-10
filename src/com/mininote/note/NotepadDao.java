package com.mininote.note;

import java.util.List;

import com.mininote.model.Notepad;

public interface NotepadDao {
	
	// 전체 노트 검색
	List<Notepad> selectAll();
	
	// 인덱스 검색 - 필요없을 듯..
	Notepad select(int index);
	
	// 새 노트 저장
	int insert(Notepad note);
	
	// 노트 수정
	int update(int index, Notepad note);
	
	// 노트 삭제
	int delete(Notepad note);

}
