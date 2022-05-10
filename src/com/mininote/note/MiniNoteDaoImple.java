package com.mininote.note;

import static com.mininote.note.SqlQuery.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mininote.model.Notpad;
import com.mininote.model.OneLineText;

import oracle.jdbc.OracleDriver;

public class MiniNoteDaoImple implements MiniNoteDao {
	
	// Singleton 디자인 패턴 적용
	private static MiniNoteDaoImple instance = null;
	private MiniNoteDaoImple() {
		try {
			// Oracle JDBC 드라이버를 등록
			DriverManager.registerDriver(new OracleDriver());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static MiniNoteDaoImple getInstance() {
		if (instance == null) {
			instance = new MiniNoteDaoImple();
		}
		
		return instance;
	}

	private void closeDbResources(Connection conn, Statement stmt) {
		try {
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void closeDbResources(Connection conn, Statement stmt, ResultSet rs) {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<OneLineText> selectAll() {
		List<OneLineText> textList = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_ALL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int lineNum = rs.getInt(1);
				String oneLineStr = rs.getString(2);
				String creationDate = rs.getString(3);
				
				OneLineText text = new OneLineText(lineNum, oneLineStr, creationDate);
				textList.add(text);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDbResources(conn, pstmt, rs);
		}
		
		return textList;
	}
	
	@Override
	public OneLineText select(String date) {
		OneLineText line = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_BY_DATE);
			pstmt.setString(1, date);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int lineNum = rs.getInt(1);
				String text = rs.getString(2);
				String creDate = rs.getString(3);
				
				line = new OneLineText(lineNum, text, creDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDbResources(conn, pstmt, rs);
		}
		return line;
	}

	@Override
	public int insert(OneLineText text) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pstmt = conn.prepareStatement(SQL_INSERT);
			pstmt.setString(1, text.getOneLineStr());
			pstmt.setString(2, text.getCreationDate());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDbResources(conn, pstmt);
		}
		
		return result;
	}

	@Override
	public int update(OneLineText text) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pstmt = conn.prepareStatement(SQL_UPDATE);
			pstmt.setString(1, text.getOneLineStr());
			pstmt.setString(2, text.getCreationDate());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDbResources(conn, pstmt);
		}
		return result;
	}

	@Override
	public int delete(String date) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_DELETE);
			pstmt.setString(1, date);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDbResources(conn, pstmt);
		}
		
		return result;
	}
	
	@Override
	public List<Notpad> selectAllNote() {
		List<Notpad> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_ALL_NOTE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int cid = rs.getInt(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				String date = rs.getString(4);
				
				Notpad noteInfo = new Notpad(cid, title, content, date);
				list.add(noteInfo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDbResources(conn, pstmt, rs);
		}

		return list;
	}
	
	@Override
	public Notpad selectNote(Notpad note) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int insertNote(Notpad note) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pstmt = conn.prepareStatement(SQL_INSERT_NOTE);
			pstmt.setString(1, note.getTitle());
			pstmt.setString(2, note.getContent());
			pstmt.setString(3, note.getUpdateDate());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDbResources(conn, pstmt);
		}
		
		return result;
		
	}
	
	@Override
	public int updateNote(Notpad note) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int deleteNote(int noteNum) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_DELETE_NOTE);
			pstmt.setInt(1, noteNum);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDbResources(conn, pstmt);
		}
		return result;
	}

}
