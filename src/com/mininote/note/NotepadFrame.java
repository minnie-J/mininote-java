package com.mininote.note;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.mininote.model.Notepad;

public class NotepadFrame extends JFrame {
	
	interface NoteHandler {
		void loadNotepadData();
	}

	private static JFrame frame;
	private JPanel notepadPane;
	private JTextField inputTitleField;
	private JTextArea inputText;
	private boolean onOff;
	
	private NoteHandler handler;

	/**
	 * Launch the application.
	 */
	public static void showModiNoteFrame(NoteHandler handler, int id, Notepad note) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new NotepadFrame(handler, id, note);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} // end showNoteFrame()
	
	public static void showReadOnlyNoteFrame(NoteHandler handler, int id, Notepad note) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new NotepadFrame(handler, id, note);
					if(id == 1) {
						frame.repaint();
					} else {
						frame.setVisible(true);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} // end showNoteFrame()
	
	
	/**
	 * Create the frame.
	 */
	public NotepadFrame(NoteHandler handler, int id, Notepad note) {
		this.handler = handler;
		
		setTitle("NOTE");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(800, 350, 500, 430);
		notepadPane = new JPanel();
		notepadPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(notepadPane);
		GridBagLayout gbl_notepadPane = new GridBagLayout();
		gbl_notepadPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_notepadPane.rowHeights = new int[]{0, 0, 0};
		gbl_notepadPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_notepadPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		notepadPane.setLayout(gbl_notepadPane);
		
		// Always On Top ??????
		JToggleButton tglbtnAot = new JToggleButton("?????? ??????");
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
		notepadPane.add(tglbtnAot, gbc_btnAot);
		
		// ?????? ????????? ??????
		inputTitleField = new JTextField();
		if (id != 0) { // ??? ?????? ????????????
			inputTitleField.setEditable(false);
			inputTitleField.setText(note.getTitle());
		} else { // ??? ?????? ???
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
		inputTitleField.setEditable(true);
		inputTitleField.setText("(????????????)");
		inputTitleField.requestFocusInWindow();
		}
		inputTitleField.setOpaque(false);
		inputTitleField.setFont(new Font("??????????????????", Font.PLAIN, 18));
		inputTitleField.setBorder(new EmptyBorder(0, 2, 0, 0));
		GridBagConstraints gbc_inputTitleField = new GridBagConstraints();
		gbc_inputTitleField.gridwidth = 11;
		gbc_inputTitleField.insets = new Insets(0, 0, 5, 5);
		gbc_inputTitleField.fill = GridBagConstraints.BOTH;
		gbc_inputTitleField.gridx = 0;
		gbc_inputTitleField.gridy = 0;
		notepadPane.add(inputTitleField, gbc_inputTitleField);
		inputTitleField.setColumns(10);
		
		
		// readOnly?????? popup ??????
		final JPopupMenu popup = new JPopupMenu();
		popup.add(new JMenuItem(new AbstractAction("??? ??????") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: ?????? ?????? ???????????? ??? ??????

			}
		}));
		popup.add(new JMenuItem(new AbstractAction("Export") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: ?????? ?????? ???????????? ??? ??????

			}
		}));
		popup.add(new JMenuItem(new AbstractAction("??????") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: ?????? ?????? ???????????? ??? ??????
				int confirm = JOptionPane.showConfirmDialog(
						frame, "?????????????????????????", "Delete", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE
						);
				if (confirm == JOptionPane.YES_OPTION) {
					NotepadDaoImple noteDao = NotepadDaoImple.getInstance();
					noteDao.delete(note);
					handler.loadNotepadData();
					dispose();
					
				}

			}
		}));
		
		// ??????/??????,export,?????? ??????
		JButton btnAction = new JButton("");
		if (id != 0) { // ??? ?????? ?????????
			btnAction.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			});
			btnAction.setIcon(new ImageIcon(".\\images\\save.png"));
		} else { // ????????????
			btnAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sendUpdateNoteData();
				}
			});
			btnAction.setIcon(new ImageIcon(".\\images\\save.png"));
		}
		btnAction.setHorizontalAlignment(SwingConstants.RIGHT);
		btnAction.setContentAreaFilled(false);
		btnAction.setBorderPainted(false);
		btnAction.setFocusPainted(false);
		btnAction.setOpaque(false);
		GridBagConstraints gbc_btnAction = new GridBagConstraints();
		gbc_btnAction.insets = new Insets(0, 0, 5, 0);
		gbc_btnAction.anchor = GridBagConstraints.WEST;
		gbc_btnAction.gridx = 14;
		gbc_btnAction.gridy = 0;
		notepadPane.add(btnAction, gbc_btnAction);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 15;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		notepadPane.add(scrollPane, gbc_scrollPane);
		
		// ??? ??????
		inputText = new JTextArea();
		if (id != 0) { // ??? ?????? ?????? ???
			inputText.setOpaque(false);
			inputText.setEditable(false);
			inputText.setText(note.getContent());
		} else { // ??? ?????????
			inputText.setOpaque(true);
			inputText.setEditable(true);
		}
		inputText.setBorder(new EmptyBorder(3, 5, 3, 0));
		inputText.setFont(new Font("?????????????????? Light", Font.PLAIN, 14));
		scrollPane.setViewportView(inputText);
	} // end NotepadFrame()
	
	private void sendUpdateNoteData() {
		String title = inputTitleField.getText();
		String content = inputText.getText();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA);

		Date currentTime = new Date();
		String curTime = formatter.format(currentTime);

		Notepad note = new Notepad(null, title, content, curTime);

		NotepadDaoImple noteDao = NotepadDaoImple.getInstance();
		noteDao.insert(note);
		handler.loadNotepadData();
		
		//TODO: noteInfo??? ??????????????? setNoteButton()??? ?????????..
		
		showReadOnlyNoteFrame(handler, 1, note);
		
	} // end sendNewNoteDate()

}
