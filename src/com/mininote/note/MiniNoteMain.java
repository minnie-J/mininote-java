package com.mininote.note;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.mininote.model.Notepad;
import com.mininote.model.Notpad;
import com.mininote.model.OneLineMemo;
import com.mininote.model.OneLineText;
import com.mininote.note.InsertNoteFrame.InsertHandler;
import com.mininote.note.MemoFrame.SaveMemoHandler;
import com.mininote.note.NotepadFrame.NoteHandler;
import com.mininote.note.ReadNoteFrame.UpdateHandler;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.LineBorder;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;

import javax.swing.JCheckBox;
import java.awt.Toolkit;
import java.awt.Insets;

public class MiniNoteMain implements SaveMemoHandler, NoteHandler {

	public static JFrame frame;
	
	private NotepadDaoImple noteDao;
	private OneLineMemoDaoImple memoDao;
	
	private static final String PANEL1 = "coverPane";
	private static final String PANEL2 = "notePane";
	
	
	private JPanel fieldPanel_1;
	public static JPanel fieldPanel_2;

	int memoDataSize;
	int pointer = 10;
	int notePointer;
	int panel_1Height = 274; 
	int panel_2Height;
	int onOff = 0;
	int selectedTextNum;
	private JPanel oneLinePanel;
	private JTextField txtTest;
	
	public static MiniNoteMain window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
				} catch (Exception e) {
					System.out.println("Substance Graphite failed to initialize");
				}

			}
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MiniNoteMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} // end main()

	/**
	 * Create the application.
	 */
	public MiniNoteMain() {
		noteDao = NotepadDaoImple.getInstance();
		try {
			memoDao = OneLineMemoDaoImple.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// 프레임
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\SkyDrive\\아이티윌강의관련\\1. JAVA\\Project\\완성\\images\\note.png"));
		frame.setResizable(false);
		frame.setTitle("MININOTE");
		frame.setBounds(800, 350, 300, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel coverPanel = new JPanel();
		frame.getContentPane().add(coverPanel, PANEL1);
		coverPanel.setLayout(null);
		
		JButton btnEnter = new JButton("시작하기");
		btnEnter.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnEnter.setContentAreaFilled(false);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadOneLineMemoData();
				loadNotepadData();
				CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), PANEL2);
			}
		});
		btnEnter.setActionCommand("시작");
		btnEnter.setBounds(110, 240, 68, 34);
		coverPanel.add(btnEnter);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(".\\images\\note.png"));
		lblNewLabel.setBounds(79, 53, 110, 120);
		coverPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<html><center>M I N I<br>NOTE");
		lblNewLabel_1.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_1.setFont(new Font("Quicksand Bold", Font.PLAIN, 18));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(94, 170, 99, 46);
		coverPanel.add(lblNewLabel_1);
		
		JButton btnTest = new JButton("스킨변환테스트");
		
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame.setDefaultLookAndFeelDecorated(true);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");
							
						} catch (Exception e) {
							System.out.println("Substance Graphite failed to initialize");
						}

					}
				});
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
//							MiniNoteMain window = new MiniNoteMain();
//							window.frame.setVisible(true);
							window.frame.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnTest.setBounds(123, 10, 159, 23);
		coverPanel.add(btnTest);
		
		JLabel lblCopyright = new JLabel("M.H.JEONG이 만듦");
		lblCopyright.setBounds(172, 316, 110, 15);
		coverPanel.add(lblCopyright);
		// 여기까지 프로그램 커버
		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, PANEL2);
		panel.setLayout(null);
		
		JPanel subMenuPanel = new JPanel();
		subMenuPanel.setBounds(145, 0, 145, 20);
		panel.add(subMenuPanel);
		subMenuPanel.setLayout(null);
		
		// Always On Top 버튼
		JLabel lblAot = new JLabel("Always On Top");
		lblAot.setForeground(Color.DARK_GRAY);
		lblAot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (onOff == 1) {
					frame.setAlwaysOnTop(false);
					lblAot.setForeground(Color.DARK_GRAY);
					onOff = 0;
				} else {
					frame.setAlwaysOnTop(true);
					lblAot.setForeground(Color.LIGHT_GRAY);
					onOff = 1;
				}
			}
		});
		lblAot.setBounds(50, 5, 90, 15);
		lblAot.setHorizontalAlignment(SwingConstants.CENTER);
		subMenuPanel.add(lblAot);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 294, 341);
		panel.add(tabbedPane);
		
		oneLinePanel = new JPanel();
		tabbedPane.addTab("One-Line", null, oneLinePanel, null);
		oneLinePanel.setLayout(null);
		oneLinePanel.setPreferredSize(new Dimension(302, 372));
		
		JPanel titlePanel_1 = new JPanel();
		titlePanel_1.setBackground(Color.WHITE);
		titlePanel_1.setBounds(0, 0, 289, 40);
		oneLinePanel.add(titlePanel_1);
		titlePanel_1.setLayout(null);
		
		// 새 한줄 메모 버튼
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(".\\images\\new.png"));
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertNewMemo();
			}
		});
		btnNewButton_1.setBounds(244, 2, 36, 36);
		titlePanel_1.add(btnNewButton_1);
		
		// 한줄 메모 타이틀 라벨
		JLabel lblTitle_1 = new JLabel("");
		lblTitle_1.setIcon(new ImageIcon(".\\images\\lbl_1.png"));
		lblTitle_1.setBounds(0, 0, 289, 40);
		titlePanel_1.add(lblTitle_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);    //스크롤 속도
		scrollPane.setBounds(0, 38, 289, 274);
		oneLinePanel.add(scrollPane);
		
		fieldPanel_1 = new JPanel();
		fieldPanel_1.setPreferredSize(new Dimension(289, panel_1Height));
		scrollPane.setViewportView(fieldPanel_1);
		fieldPanel_1.setLayout(null);
		
		JPanel notePadPanel = new JPanel();
		notePadPanel.setEnabled(false);
		tabbedPane.addTab("Notepad", null, notePadPanel, null);
		notePadPanel.setLayout(null);
		
		JPanel titlePanel_2 = new JPanel();
		titlePanel_2.setLayout(null);
		titlePanel_2.setBackground(Color.WHITE);
		titlePanel_2.setBounds(0, 0, 289, 40);
		notePadPanel.add(titlePanel_2);
		
		// 새 노트패드 버튼
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertNewNote();
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(".\\images\\new.png"));
		btnNewButton_2.setFocusPainted(false);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setBounds(244, 2, 36, 36);
		titlePanel_2.add(btnNewButton_2);
		
		// 노트패드 타이틀 라벨
		JLabel lblTitle_2 = new JLabel("");
		lblTitle_2.setIcon(new ImageIcon(".\\images\\lbl_2.png"));
		lblTitle_2.setBounds(0, 0, 289, 40);
		titlePanel_2.add(lblTitle_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(0, 38, 289, 274);
		notePadPanel.add(scrollPane_2);
		
		fieldPanel_2 = new JPanel();
		fieldPanel_2.setPreferredSize(new Dimension(289, panel_2Height));
		fieldPanel_2.setLayout(null);
//		fieldPanel_2.setPreferredSize(new Dimension(289, 270));
		scrollPane_2.setViewportView(fieldPanel_2);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(110, 106, 97, 23);
		panel_1.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.setBounds(57, 72, 170, 145);
		panel_1.add(btnNewButton_4);
		
		txtTest = new JTextField();
		txtTest.setFont(new Font("굴림", Font.PLAIN, 14));
		txtTest.setText("test");
		txtTest.setBounds(66, 260, 116, 21);
		panel_1.add(txtTest);
		txtTest.setColumns(10);

	} // end initialize()
	
	
//------------------------------------------------------- 전체 데이터 불러오기
	
	// 한 줄 메모 전체 데이터 불러오기
	public void loadOneLineMemoData() {
		fieldPanel_1.removeAll();
		fieldPanel_1.revalidate();
		fieldPanel_1.repaint();
		
		memoDataSize = 0;
		pointer = 10;
		panel_1Height = 270;
		List<OneLineMemo> list = memoDao.selectAll();
		for (int i = 0; i < list.size(); i++) {
			Boolean isImportant = list.get(i).isImportant();
			Boolean toDo = list.get(i).isToDo();
			String memo = list.get(i).getMemo();
			String creDate = list.get(i).getCreationDate();
			OneLineMemo memoData = new OneLineMemo(isImportant, toDo, memo, creDate);
			setTextFieldBg();
			setTextField(i, memoData);
			setDateAndDeleteButton(i, creDate);
		}
		memoDataSize = list.size();
//		System.out.println("메모 내용: " + list.get(0).getMemo());
//		System.out.println("저장 날짜: " + list.get(0).getCreationDate());
	}
	
	// 노트패드 전체 데이터 불러오기
	@Override
	public void loadNotepadData() {
		fieldPanel_2.removeAll();
		fieldPanel_2.revalidate();
		fieldPanel_2.repaint();
		 notePointer = 0;
		 panel_2Height = 267;
		List<Notepad> noteList = noteDao.selectAll();
		for (int i = 0; i < noteList.size(); i++) {
			String fileName = noteList.get(i).getFileName();
			String title = noteList.get(i).getTitle();
			String content = noteList.get(i).getContent();
			String lastModiDate = noteList.get(i).getLastModiDate();
			Notepad note = new Notepad(fileName, title, content, lastModiDate);			
			
			setNewNoteButton(note);
			
		}
	}
	
//--------------------------------------------------------- 한 줄 메모
	
	// 한줄 메모 체크박스(별 이미지)
	private void setOnOffIcon(int index, OneLineMemo memoData,JTextField field) {
		JCheckBox chckbx = new JCheckBox("");
		if (memoData.isToDo() == false) {
			chckbx.setSelectedIcon(new ImageIcon(".\\images\\star_2.png"));
			chckbx.setSelected(true);
			field.setForeground(Color.GRAY);
			
		} else {
			if (memoData.isImportant() == true) {
				chckbx.setIcon(new ImageIcon(".\\images\\star_0.png"));
			} else {
				chckbx.setIcon(new ImageIcon(".\\images\\star_1.png"));
			}
			
			chckbx.setSelected(false);
			field.setForeground(Color.LIGHT_GRAY);
		}
		chckbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!chckbx.isSelected()) {
					chckbx.setIcon(new ImageIcon(".\\images\\star_1.png"));
					field.setForeground(Color.LIGHT_GRAY);
					memoData.setToDo(true);
				} else {
					chckbx.setSelectedIcon(new ImageIcon(".\\images\\star_2.png"));
					field.setForeground(Color.GRAY);
					memoData.setImportant(false);
					memoData.setToDo(false);
				}
				memoDao.toDoUpdate(index, memoData);
			}
		});
	chckbx.setPressedIcon(null);
	chckbx.setFocusPainted(false);
	chckbx.setContentAreaFilled(false);
	chckbx.setBounds(10, pointer+3, 32, 19);
	fieldPanel_1.add(chckbx);
	}
	
	// 한줄메모 밑줄 이미지(UI)
	private void setTextFieldBg() {
		JLabel lblBg = new JLabel("");
		lblBg.setIcon(new ImageIcon(".\\images\\line.png"));
		lblBg.setBounds(36, pointer+4, 226, 21);
		fieldPanel_1.add(lblBg);
	}
	
	// 한 줄 메모 텍스트 필드 셋
	private void setTextField(int index, OneLineMemo memoData) {
		JTextField inputTextField = new JTextField();
//		inputTextField.setDocument((new JTextFieldLimit(16)));
		inputTextField.setEditable(false);
		inputTextField.setBorder(null);
		inputTextField.setText(memoData.getMemo());
		setOnOffIcon(index, memoData, inputTextField);
		inputTextField.setBounds(38, pointer, 190, 21);
		fieldPanel_1.add(inputTextField);
		inputTextField.setColumns(10);
		
		inputTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					updateOneLineMemo(index, memoData);
				} 
			}
		});
		
	}
	
	// 날짜 및 삭제 버튼
	private void setDateAndDeleteButton(int index, String date) {
		JButton inputDateButton = new JButton();
		LocalDateTime datetime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		String shortDate = datetime.format(DateTimeFormatter.ofPattern("MM/dd"));
		inputDateButton.setText(shortDate);
		inputDateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				inputDateButton.setText("　×");
				inputDateButton.setFont(new Font("굴림", Font.BOLD, 14));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				inputDateButton.setText(shortDate);
				inputDateButton.setFont(new Font("굴림", Font.PLAIN, 10));
			}
		});
		inputDateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteText(index);
			}
		});
		inputDateButton.setVerticalAlignment(SwingConstants.BOTTOM);
		inputDateButton.setFont(new Font("굴림", Font.PLAIN, 10));
		inputDateButton.setBorder(null);
		inputDateButton.setContentAreaFilled(false);
		inputDateButton.setBorderPainted(false);
		inputDateButton.setOpaque(false);
		inputDateButton.setForeground(Color.DARK_GRAY);
		inputDateButton.setBounds(231, pointer, 32, 19);
		inputDateButton.setFocusPainted(false);
		fieldPanel_1.add(inputDateButton);
		fieldPanel_1.revalidate();
		fieldPanel_1.repaint();
		
		pointer = pointer + 27;
		if (pointer > 270) {
			panel_1Height = panel_1Height + 26;
			fieldPanel_1.setPreferredSize(new Dimension(289, panel_1Height));
		}
	}
	
	// 한 줄 메모 새글 입력 설정
	private void insertNewMemo() {
		MemoFrame.showMemoFrame(this);		

	} // end insertNewMemo()
	//TODO: 텍스트 필드길이만큼으로 글자 수 제한 방법 찾아보기
	
//	private void selectThisTextInfo(OneLineText line) {
//		dao.select(line.getCreationDate());
//	}
	
	// 한 줄 메모 수정
	private void updateOneLineMemo(int index, OneLineMemo memoData) {
//		OneLineText line = new OneLineText(str, date);
		MemoFrame.updateMemoFrame(this, index, memoData);
//		memoDao.update(this, index, memoData);
	}
	
	// 한 줄 메모 삭제
	private void deleteText(int index) {
		int confirm = JOptionPane.showConfirmDialog(
				frame, "삭제하시겠습니까?", "Delete", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.WARNING_MESSAGE
				);
		if (confirm == JOptionPane.YES_OPTION) {
			memoDao.delete(index);
			
			pointer = pointer - 26;
			panel_1Height = panel_1Height - 25;
			fieldPanel_1.setPreferredSize(new Dimension(289, panel_1Height));
			
			loadOneLineMemoData();
			
		}
	}
	
	
	
//------------------------------------------------------------ 노트패드
	//TODO: 노트패드 메뉴 file용으로 전체 수정
	
	
		// 노트패드 새글 입력용 새창 호출
		private void InsertNewNote() {
			NotepadFrame.showModiNoteFrame(this, 0, null);
		}
		
		// 노트패드 글목록 버튼 클릭시 해당 글 호출
		private void ReadThisNote(Notepad note) {
			NotepadFrame.showReadOnlyNoteFrame(this, 2, note);
		}
		
		// 노트패드 글목록 버튼 셋
		private void setNewNoteButton(Notepad note) {
			JButton btnNewNote = new JButton();
			btnNewNote.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ReadThisNote(note);
				}
			});
			btnNewNote.setFont(new Font("나눔바른고딕", Font.PLAIN, 14));
			String content = note.getContent();
			String noteDate = note.getLastModiDate();
			LocalDateTime datetime = LocalDateTime.parse(noteDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
			String shortDate = datetime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일  a hh시mm분  저장"));
	
			if (content.length() >= 23) {
				content = note.getContent().substring(0, 23) + "......";
			} else {
				content = note.getContent();
			}
			
			btnNewNote.setText(
					"<html><b>" + note.getTitle() +"</b><br>"
					+ "<font color=\"#a4a4a4\"><i>"+ content + "</i></font><br>"
					+ "<font color=\"#6e6e6e\"><small>" + shortDate + "</small></font></html>");
			btnNewNote.setContentAreaFilled(false);
//			btnNewNote.setMargin(new Insets(0, 14, 2, 14));
//			btnNewNote.setBorder(new LineBorder(Color.GRAY));
			btnNewNote.setHorizontalAlignment(SwingConstants.LEFT);
			btnNewNote.setBounds(-6, notePointer, 300, 66);
			fieldPanel_2.add(btnNewNote);
			
			notePointer = notePointer + 66;
			if (notePointer > 275) {
				panel_2Height = panel_2Height + 66;
				fieldPanel_2.setPreferredSize(new Dimension(289, panel_2Height));
			}
			
		}
		
		// 노트패드 글삭제
//			private void deleteNote(int index, Notepad note) {
//				int confirm = JOptionPane.showConfirmDialog(
//						frame, "삭제하시겠습니까?", "Delete", 
//						JOptionPane.YES_NO_OPTION, 
//						JOptionPane.WARNING_MESSAGE
//						);
//				if (confirm == JOptionPane.YES_OPTION) {
//					noteDao.delete(index, note);
//					loadNotepadData();
//					
//				}
//			}

	
} // end class MiniNoteMain
