package com.mininote.note;

import java.util.List;

import com.mininote.model.OneLineMemo;

public interface OneLineMemoDao {
	
	// 전체 검색
	List<OneLineMemo> selectAll();
	
	// 인덱스 검색
	OneLineMemo select(int index);
	
	// 새 메모 입력
	int insert(OneLineMemo memo);
	
	// To Do 체크/해제 여부 저장
	int toDoUpdate(int index, OneLineMemo memo);
	
	// 메모 수정
	int update(int index, OneLineMemo memo);

	// 메모 삭제
	int delete(int index);
	
}
