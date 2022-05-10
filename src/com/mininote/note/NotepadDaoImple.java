package com.mininote.note;

import java.util.List;

import com.mininote.model.Notepad;
import com.mininote.util.FileUtil;

public class NotepadDaoImple implements NotepadDao {
	
	private List<Notepad> noteList;
	
	private static NotepadDaoImple instance = null;
	private NotepadDaoImple() {
		noteList = FileUtil.loadNoteData();
	}
	
	public static NotepadDaoImple getInstance() {
		if (instance == null) {
			instance = new NotepadDaoImple();
		}
		
		return instance;
	}

	@Override
	public List<Notepad> selectAll() {
		return noteList;
	}

	@Override
	public Notepad select(int index) {
		if (index >= 0 && index < noteList.size()) {
			return noteList.get(index);
		} else {
			return null;
		}
	}

	@Override
	public int insert(Notepad note){
		
		noteList.add(note);
		try {
			FileUtil.writeNotepadDataToFile(note);
		} catch (Exception e) {
			System.out.println(e.getMessage() + "insert파일쓰기오류");
		}
		
		return 1;
	}

	@Override
	public int update(int index, Notepad note) {
		noteList.get(index).setTitle(note.getTitle());
		noteList.get(index).setContent(note.getContent());
		try {
			FileUtil.writeNotepadDataToFile(note);
		} catch (Exception e) {
			System.out.println(e.getMessage() + "update파일쓰기오류");
		}
		return 1;
	}

	@Override
	public int delete(Notepad note) {
		int listSize = noteList.size();
		FileUtil.deleteNote(listSize, note);
		
		return 1;
	}

}
