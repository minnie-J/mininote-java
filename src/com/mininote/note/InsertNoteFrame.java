package com.mininote.note;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mininote.model.Notpad;
import com.mininote.model.OneLineText;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.TextArea;

import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JToggleButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InsertNoteFrame extends JFrame {
	
	interface InsertHandler {
		void loadNotepadData();
	}

	private JPanel insertNotePane;
	private JTextField inputTitleField;
	private JTextArea inputText;
	private boolean onOff; 
	
	private InsertHandler handler;

	/**
	 * Launch the application.
	 */
	public static void showInsertNoteFrame(InsertHandler handler) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertNoteFrame frame = new InsertNoteFrame(handler);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} // end showInsertNoteFrame()

	/**
	 * Create the frame.
	 */
	public InsertNoteFrame(InsertHandler handler) {
		this.handler = handler;
		
		setTitle("NOTE");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(800, 350, 500, 430);
		insertNotePane = new JPanel();
		insertNotePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(insertNotePane);
		GridBagLayout gbl_insertNotePane = new GridBagLayout();
		gbl_insertNotePane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_insertNotePane.rowHeights = new int[]{0, 0, 0};
		gbl_insertNotePane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_insertNotePane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		insertNotePane.setLayout(gbl_insertNotePane);
		
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
		insertNotePane.add(tglbtnAot, gbc_btnAot);
		
		inputTitleField = new JTextField();
		inputTitleField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					inputText.requestFocus();
				}
			}
		});
		inputTitleField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputTitleField.select(0, inputTitleField.getText().length());
			}
		});
		inputTitleField.setText("(제목없음)");
		inputTitleField.requestFocusInWindow();
		inputTitleField.setOpaque(false);
		inputTitleField.setFont(new Font("나눔바른고딕", Font.PLAIN, 18));
		inputTitleField.setBorder(new EmptyBorder(0, 2, 0, 0));
		GridBagConstraints gbc_inputTitleField = new GridBagConstraints();
		gbc_inputTitleField.gridwidth = 11;
		gbc_inputTitleField.insets = new Insets(0, 0, 5, 5);
		gbc_inputTitleField.fill = GridBagConstraints.BOTH;
		gbc_inputTitleField.gridx = 0;
		gbc_inputTitleField.gridy = 0;
		insertNotePane.add(inputTitleField, gbc_inputTitleField);
		inputTitleField.setColumns(10);
		
		JButton btnSave = new JButton("");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendNewNoteData();
			}
		});
		btnSave.setHorizontalAlignment(SwingConstants.RIGHT);
		btnSave.setContentAreaFilled(false);
		btnSave.setBorderPainted(false);
		btnSave.setFocusPainted(false);
		btnSave.setOpaque(false);
		btnSave.setIcon(new ImageIcon(".\\images\\save.png"));
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 0);
		gbc_btnSave.anchor = GridBagConstraints.WEST;
		gbc_btnSave.gridx = 14;
		gbc_btnSave.gridy = 0;
		insertNotePane.add(btnSave, gbc_btnSave);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 15;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		insertNotePane.add(scrollPane, gbc_scrollPane);
		
		inputText = new JTextArea();
		inputText.setBorder(new EmptyBorder(3, 5, 3, 0));
		inputText.setFont(new Font("나눔바른고딕 Light", Font.PLAIN, 14));
		scrollPane.setViewportView(inputText);
	} // end InsertNoteFrame()
	
	private void sendNewNoteData() {
		String title = inputTitleField.getText();
		String content = inputText.getText();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA);

		Date currentTime = new Date();
		String curTime = formatter.format(currentTime);

		Notpad noteInfo = new Notpad(title, content, curTime);

		MiniNoteDaoImple dao = MiniNoteDaoImple.getInstance();
		dao.insertNote(noteInfo);
		handler.loadNotepadData();
		
		//TODO: noteInfo를 메인클래스 setNoteButton()로 보내야..
		
		dispose();
		
	} // end sendNewNoteDate()
	
} // end class InsertNoteFrame
