package com.mininote.note;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.mininote.model.Notpad;
import com.mininote.note.InsertNoteFrame.InsertHandler;

public class ReadNoteFrame extends JFrame {

	public interface UpdateHandler {
//		void getNotepadData(Notepad note);
		//TODO: ??
	}

	private JPanel readNotePane;
	private JTextField readTitleField;
	private JTextArea updateText;
	private boolean onOff;

	private UpdateHandler handler;

	public static void showReadNoteFrame(UpdateHandler handler, Notpad note) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReadNoteFrame frame = new ReadNoteFrame(handler, note);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	} // end showReadNoteFrame()

	public ReadNoteFrame(UpdateHandler handler, Notpad note) {
		this.handler = handler;

		setTitle("NOTE");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(800, 350, 500, 430);
		readNotePane = new JPanel();
		readNotePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(readNotePane);
		GridBagLayout gbl_readNotePane = new GridBagLayout();
		gbl_readNotePane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_readNotePane.rowHeights = new int[] { 0, 0, 0 };
		gbl_readNotePane.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_readNotePane.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		readNotePane.setLayout(gbl_readNotePane);

		// '항상위에' 버튼
		JToggleButton tglbtnAot = new JToggleButton("항상 위에");
		tglbtnAot.setRolloverEnabled(false);
		tglbtnAot.setFocusPainted(false);
		tglbtnAot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (onOff == true) {

					setAlwaysOnTop(false);
					tglbtnAot.setForeground(Color.DARK_GRAY);
					onOff = false;
				} else {
					setAlwaysOnTop(true);
					tglbtnAot.setForeground(Color.LIGHT_GRAY);
					onOff = true;
				}
			}
		});
		tglbtnAot.setBorder(new EmptyBorder(0, 3, 0, 2));
		GridBagConstraints gbc_btnAot = new GridBagConstraints();
		gbc_btnAot.ipady = 5;
		gbc_btnAot.gridwidth = 3;
		gbc_btnAot.anchor = GridBagConstraints.EAST;
		gbc_btnAot.insets = new Insets(0, 0, 5, 5);
		gbc_btnAot.gridx = 11;
		gbc_btnAot.gridy = 0;
		readNotePane.add(tglbtnAot, gbc_btnAot);

		// 글 제목
		readTitleField = new JTextField();
		readTitleField.setEditable(false);
//		readTitleField.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_TAB) {
//					updateText.requestFocus();
//				}
//			}
//		});
//		readTitleField.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				readTitleField.select(0, readTitleField.getText().length());
//			}
//		});
		readTitleField.setText(note.getTitle());
//		readTitleField.requestFocusInWindow();
		readTitleField.setOpaque(false);
		readTitleField.setFont(new Font("나눔바른고딕", Font.PLAIN, 18));
		readTitleField.setBorder(new EmptyBorder(0, 2, 0, 0));
		GridBagConstraints gbc_readTitleField = new GridBagConstraints();
		gbc_readTitleField.gridwidth = 11;
		gbc_readTitleField.insets = new Insets(0, 0, 5, 5);
		gbc_readTitleField.fill = GridBagConstraints.BOTH;
		gbc_readTitleField.gridx = 0;
		gbc_readTitleField.gridy = 0;
		readNotePane.add(readTitleField, gbc_readTitleField);
		readTitleField.setColumns(10);

		//TODO: 수정/삭제 팝업 메뉴로 바꾸기
		// popup 메뉴
		final JPopupMenu popup = new JPopupMenu();
		popup.add(new JMenuItem(new AbstractAction("Modification") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: 수정 버튼 클릭했을 때 동작
				readTitleField.setEditable(true);;
				updateText.setEditable(true);
				updateText.setOpaque(true);
			}
		}));
		popup.add(new JMenuItem(new AbstractAction("Delete") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: 삭제 버튼 클릭했을 때 동작
				
			}
		}));
		// 수정, 삭제 버튼
		JButton btnModifi = new JButton("");
		btnModifi.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		btnModifi.setHorizontalAlignment(SwingConstants.RIGHT);
		btnModifi.setContentAreaFilled(false);
		btnModifi.setBorderPainted(false);
		btnModifi.setFocusPainted(false);
		btnModifi.setOpaque(false);
		btnModifi.setIcon(new ImageIcon(".\\images\\save.png"));
		GridBagConstraints gbc_btnModifi = new GridBagConstraints();
		gbc_btnModifi.insets = new Insets(0, 0, 5, 0);
		gbc_btnModifi.anchor = GridBagConstraints.WEST;
		gbc_btnModifi.gridx = 14;
		gbc_btnModifi.gridy = 0;
		readNotePane.add(btnModifi, gbc_btnModifi);
		//여기까지 btnModifi

		// 글 내용
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 15;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		readNotePane.add(scrollPane, gbc_scrollPane);

		updateText = new JTextArea();
		updateText.setOpaque(false);
//		updateText.setVerifyInputWhenFocusTarget(false);
		updateText.setEditable(false);
		updateText.setText(note.getContent());
		updateText.setBorder(new EmptyBorder(3, 5, 3, 0));
		updateText.setFont(new Font("나눔바른고딕 Light", Font.PLAIN, 14));
		scrollPane.setViewportView(updateText);
	} // end readNoteFrame()
	
//	private void sendNewNoteData() {
//		String title = readTitleField.getText();
//		String content = updateText.getText();
//
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA);
//
//		Date currentTime = new Date();
//		String curTime = formatter.format(currentTime);
//
//		Notepad noteInfo = new Notepad(title, content, curTime);
//
//		MiniNoteDaoImple dao = MiniNoteDaoImple.getInstance();
//		dao.insertNote(noteInfo);
//		handler.loadNotepadData();
//
		// TODO: noteInfo를 메인클래스 setNoteButton()로 보내야..
//
//		dispose();
//
//	} // end sendNewNoteDate()

} // end class UpdateNoteFrame
