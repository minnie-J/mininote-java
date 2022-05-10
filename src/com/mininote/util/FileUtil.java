package com.mininote.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.mininote.model.Notepad;
import com.mininote.model.OneLineMemo;

// 데이터 폴더는 존재하는 지, 데이터 파일은 존재하는 지
// 데이터 파일에서 ArrayList 읽어 옴, 데이터 파일에 ArrayList 씀
// FileUtil 클래스의 메소드들을 ContactDaoImple 클래스에서 사용할 예정
public class FileUtil {
	// 상수들 정의 - 데이터 폴더, 데이터 파일
	public static final String DATA_DIR = "data"; // 데이터 저장 루트 폴더
	public static final String MEMODATA_FILE = "memos.dat"; // 한줄메모 데이터 저장 확장자
	public static final String MEMO_PATH =
			DATA_DIR + File.separator + MEMODATA_FILE; // 한줄메모 데이터 저장 경로
	
	public static final String NOTEPAD_FOLDER_NAME = "notes"; // 노트패드 데이터 파일들 저장하는 폴더
	public static final String NOTEPAD_DIR = 
			DATA_DIR + File.separator + NOTEPAD_FOLDER_NAME; // 노트패드 데이터 저장 폴더 경로
	
//	public static final SimpleDateFormat formatter = 
//			new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.KOREA);
//	public static final Date currentTime = new Date();
//	public static final String saveTime = formatter.format(currentTime);
//	public static final String NOTEPAD_SAVE_FILE = saveTime + ".dat"; // 노트패드 파일 저장 확장자
//	public static final String NOTEPAD_PATH = 
//			NOTEPAD_DIR + File.separator + NOTEPAD_SAVE_FILE; // 노트패드 데이터 파일 저장 경로
	
	
	
	// 생성자
	private FileUtil() {}

	// 데이터 폴더가 있는 지 체크하고, 없으면 데이터 폴더를 생성
	public static void initDataDir() throws Exception {
		File dataDir = new File(DATA_DIR);
		if (!dataDir.exists()) { // 데이터 폴더가 없으면
			dataDir.mkdir();
		}

	} // end initDataDir()
	
	// 한줄 메모 데이터 파일에서 ArrayList 객체로 데이터를 읽고 리턴
	public static List<OneLineMemo> readMemoDataFromFile() {
		List<OneLineMemo> memoList = null;
		
		File file = new File(DATA_DIR, MEMODATA_FILE);
		InputStream in = null;
		BufferedInputStream bin = null;
		ObjectInputStream ois = null;
		try {
			in = new FileInputStream(file);
			bin = new BufferedInputStream(in);
			ois = new ObjectInputStream(bin);
			
			memoList = (ArrayList<OneLineMemo>) ois.readObject();
		} catch (Exception e) {
			System.out.println(e.getMessage() + "1");
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				System.out.println(e.getMessage() + "2");
			}
		}
		
		return memoList;
	} // end readMemoDataFromFile()
	
	// 한줄 메모 데이터 파일에 ArrayList 객체를 쓰는 메소드
	public static void writeMemoDataToFile(List<OneLineMemo> list) {
		File file = new File(MEMO_PATH);
		OutputStream out = null;
		BufferedOutputStream bout = null;
		ObjectOutputStream oos = null;
		try {
			out = new FileOutputStream(file);
			bout = new BufferedOutputStream(out);
			oos = new ObjectOutputStream(bout);
			
			
			oos.writeObject(list);
		} catch (Exception e) {
			System.out.println(e.getMessage() + "3");
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
				System.out.println(e.getMessage() + "4");
			}
		}
		
	} // end writeMemoDataToFile()
	
	// 데이터 파일이 존재하는 지 체크
	// 1) 데이터 파일이 존재하면, 파일에서 ArrayList 읽어서 리턴
	// 2) 데이터 파일이 존재하지 않으면, 비어있는 ArrayList를 리턴
	public static List<OneLineMemo> loadMemoData() {
		List<OneLineMemo> memoList = null;
		File file = new File(DATA_DIR, MEMODATA_FILE);
		if (file.exists()) { // 데이터 파일이 존재하면
			memoList = readMemoDataFromFile();
			
//			list.sort(Comparator.comparing(OneLineMemo::isImportant).thenComparing(OneLineMemo::getCreationDate));
			memoList.sort(Comparator.comparing(OneLineMemo::getCreationDate).thenComparing(OneLineMemo::isImportant));
			
		} else { // 데이터 파일이 존재하지 않으면
			memoList = new ArrayList<>();
		}
		
		return memoList;
	} // end loadMemoData()
	
//--------------------------------- ↑한줄메모 데이터 ---- ↓노트패드 데이터
	
	// 노트패드 데이터 폴더(data/notes)에서 ArrayList 객체로 데이터를 읽고 리턴
	public static List<Notepad> readNotepadDataFromFiles() {
		List<Notepad> notepadList = new ArrayList<Notepad>();
		Notepad note = new Notepad();
		note = null;
		
		File noteDir = new File(NOTEPAD_DIR);
		File[] files = noteDir.listFiles();
		
		Arrays.sort(files, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				String s1 = "";
				String s2 = "";
				
				s1 = ((File)obj1).getName();
				s2 = ((File)obj2).getName();
				
				return s1.compareTo(s2); // 오름차순
//				if(s1.compareTo(s2) == 1) {
//					return -1;
//				} else {
//					return 1;
//				} // 내림차순
			}
		}); // 노트패드 폴더 내부 파일 배열 저장
		
		for(int i = 0; i < files.length; i++) {
			File file = new File(NOTEPAD_DIR, files[i].getName());
			InputStream in = null;
			BufferedInputStream bin = null;
			ObjectInputStream ois = null;
			
			try {
				in = new FileInputStream(file);
				bin = new BufferedInputStream(in);
				ois = new ObjectInputStream(bin);
				note = (Notepad) ois.readObject();
				notepadList.add(note);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage() + "notepadList입력오류1");
			} finally {
				try {
					ois.close();
				} catch (Exception e) {
					System.out.println(e.getMessage() + "notepadList입력오류2");
				}
			}
		}
		
		return notepadList;
		
	} // end readNotepadDataFromFiles()

	// 노트패드 폴더가 있는 지 체크하고, 없으면 데이터 폴더를 생성
	public static void initNotesDir() throws Exception {
		File notesDir = new File(NOTEPAD_DIR);
		if (!notesDir.exists()) { // 데이터 폴더가 없으면
			notesDir.mkdir();
		}

	} // end initNotesDir()
	
	// 노트패드 파일 쓰기
	public static void writeNotepadDataToFile(Notepad note) throws Exception {
		initNotesDir();
		String fileName = NOTEPAD_DIR + File.separator + note.getFileName();
		File checkFile = new File(fileName);
		SimpleDateFormat formatter = 
				new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.KOREA);
		Date currentTime = new Date();
		String saveTime = formatter.format(currentTime);
		String saveFile = saveTime + ".dat"; // 노트패드 파일 저장 확장자
		String filePath = 
				NOTEPAD_DIR + File.separator + saveFile; // 노트패드 데이터 파일 저장 경로
		
		File file = null;
		if(note.getFileName() == null) {
			file = new File(filePath);
			note.setFileName(filePath);
		} else if (checkFile.exists()) {
			file = new File(fileName);
			File renameFile = new File(filePath);
			file.renameTo(renameFile);
		}
		
		OutputStream out = null;
		BufferedOutputStream bout = null;
		ObjectOutputStream oos = null;
		
		try {
			out = new FileOutputStream(file);
			bout = new BufferedOutputStream(out);
			oos = new ObjectOutputStream(bout);
			
			oos.writeObject(note);
		} catch (Exception e) {
			System.out.println(e.getMessage() + "notepad쓰기오류1");
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				System.out.println(e.getMessage() + "notepad쓰기오류2");
			}
		}
	} // end writeNotepadDataToFile()
	
//	// 노트패드 파일 수정시 마지막 저장 시간으로 파일이름 변경
//	public static File renameFile(String fileName) {
//	    File file = new File(fileName);
//	    File renameFile = new File(NOTEPAD_PATH);
//	    file.renameTo(renameFile);
//	    return file;
//	} // renameFile()
	
	//TODO: txt파일 dat로 import
	//TODO: dat파일 txt파일로 export
	
	// 노트패드 파일 삭제
	public static void deleteNote(int listSize, Notepad note) {
		String deleteNote = note.getFileName();
		File file = new File(deleteNote);
		file.delete();
		if (listSize == 1) {
			File dirFolder = new File(NOTEPAD_DIR);
			dirFolder.delete();
		}
	}
	
	// 노트패드 폴더가 존재하는 지 체크
	// 1) 폴더가 존재하면, 파일에서 ArrayList 읽어서 리턴
	// 2) 폴더가 존재하지 않으면, 비어있는 ArrayList를 리턴
	public static List<Notepad> loadNoteData() {
		List<Notepad> noteList = null;
		File notesDir = new File(NOTEPAD_DIR);
		if (notesDir.exists()) { // 노트패드 폴더가 존재하면
			noteList = readNotepadDataFromFiles();
		} else { // 노트패드 폴더가 존재하지 않으면
			noteList = new ArrayList<>();
		}
		Collections.reverse(noteList);
		return noteList;
	} // end loadMemoData()

} // end class FileUtil


