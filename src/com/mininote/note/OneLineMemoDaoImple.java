package com.mininote.note;

import java.util.List;

import com.mininote.model.OneLineMemo;
import com.mininote.util.FileUtil;

public class OneLineMemoDaoImple implements OneLineMemoDao {

	private List<OneLineMemo> list;
	
	private static OneLineMemoDaoImple instance = null;
	private OneLineMemoDaoImple() throws Exception {
		FileUtil.initDataDir();
		list = FileUtil.loadMemoData();
	}
	
	public static OneLineMemoDaoImple getInstance() throws Exception {
		if (instance == null) {
			instance = new OneLineMemoDaoImple();
		}
		
		return instance;
	}
	
//	public int getCount() {
//		return list.size();
//	}
	
	@Override
	public List<OneLineMemo> selectAll() {
		return list;
	}

	@Override
	public OneLineMemo select(int index) {
		if (index >= 0 && index < list.size()) {
			return list.get(index);
		} else {
			return null;
		}
	}

	@Override
	public int insert(OneLineMemo memoData) {
		
		list.add(memoData);
		FileUtil.writeMemoDataToFile(list);
		
		return 1;
	}
	
	@Override
	public int toDoUpdate(int index, OneLineMemo memoData) {
		list.get(index).setToDo(memoData.isToDo());
		list.get(index).setImportant(memoData.isImportant());
		
		FileUtil.writeMemoDataToFile(list);
		
		return 1;
	}

	@Override
	public int update(int index, OneLineMemo memoData) {
		list.get(index).setImportant(memoData.isImportant());
		list.get(index).setMemo(memoData.getMemo());
		
		FileUtil.writeMemoDataToFile(list);
		
		return 1;
	}

	@Override
	public int delete(int index) {
		if (index >= 0 && index < list.size()) {
			list.remove(index);
			FileUtil.writeMemoDataToFile(list);
			return 1; // 삭제 성공
		} else {
			return 0; // 삭제 실패
		}
	}

}
