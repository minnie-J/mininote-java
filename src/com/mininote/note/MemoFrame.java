package com.mininote.note;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.mininote.model.OneLineMemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import java.awt.Frame;
import javax.swing.DropMode;

public class MemoFrame extends JFrame {
	
	interface SaveMemoHandler {
		void loadOneLineMemoData();
	}
	
	private static JFrame frame;
	private JPanel inputMemoPane;
	private JTextField memoField;
	private JCheckBox cbImportant;
	
	private String memo;
	private boolean setImportant;
	
	private SaveMemoHandler handler;

	/**
	 * Launch the application.
	 */
	public static void showMemoFrame(SaveMemoHandler handler) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MemoFrame(handler, -1, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void updateMemoFrame(SaveMemoHandler handler, int index, OneLineMemo memoData) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MemoFrame(handler, index, memoData);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MemoFrame(SaveMemoHandler handler, int index, OneLineMemo memoData) {
		setAlwaysOnTop(true);
		setFocusTraversalPolicyProvider(true);
		setType(Type.UTILITY);
		this.handler = handler;
		
		setResizable(false);
		setTitle("Memo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setBounds(100, 100, 268, 126);
		setLocation(MiniNoteMain.frame.getX() + 16, MiniNoteMain.frame.getY() + 100);
		setSize(268, 130);
		inputMemoPane = new JPanel();
		inputMemoPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(inputMemoPane);
		inputMemoPane.setLayout(null);
		
		JLabel lblMemo = new JLabel("메모를 입력해주세요");
		lblMemo.setBounds(12, 10, 126, 15);
		inputMemoPane.add(lblMemo);
		memoField = new JTextField();
		memoField.setBounds(12, 35, 238, 21);
		if (memoData != null) {
			memoField.setText(memoData.getMemo());
		}
		memoField.addAncestorListener(new AncestorListener() {
			
			@Override
			public void ancestorRemoved(AncestorEvent event) {
			}
			
			@Override
			public void ancestorMoved(AncestorEvent event) {
			}
			
			@Override
			public void ancestorAdded(AncestorEvent event) {
				JComponent component = event.getComponent();
				component.requestFocus();
			}
		});
		inputMemoPane.add(memoField);
		memoField.setColumns(10);
		
		cbImportant = new JCheckBox("중요");
		
		if (memoData != null) {
			setImportant = memoData.isImportant();
			cbImportant.setSelected(setImportant);
		}
		
		cbImportant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbImportant.isSelected()) {
					setImportant = true;
				} else {
					setImportant = false;
				}
			}
		});
		cbImportant.setFocusPainted(false);
		cbImportant.setBounds(196, 6, 55, 23);
		inputMemoPane.add(cbImportant);
		
		JButton btnSubmit = new JButton("확인");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (memoField.getText() == null || memoField.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "메모가 입력되지 않았습니다!", "알림", JOptionPane.WARNING_MESSAGE);
				} 
				else {
					if (memoData != null) {
						updateMemoData(index, memoData);
					} else {
						saveMemoData();
					}
					
				}
			}
		});
		btnSubmit.setBounds(61, 66, 67, 23);
		inputMemoPane.add(btnSubmit);
		
		JButton btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(135, 66, 67, 23);
		inputMemoPane.add(btnCancel);
	} // end MemoFrame()
	
	private void saveMemoData() {
		memo = memoField.getText();
		setImportant = cbImportant.isSelected();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA);
		Date currentTime = new Date();
		String curTime = formatter.format(currentTime);
		
		OneLineMemo memoData = new OneLineMemo(setImportant, true, memo, curTime);
		try {
			OneLineMemoDaoImple memoDao = OneLineMemoDaoImple.getInstance();
			memoDao.insert(memoData);
			handler.loadOneLineMemoData();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dispose();
	} // end saveMemoData()
	
	private void updateMemoData(int index, OneLineMemo memoData) {
		memo = memoField.getText();
		setImportant = cbImportant.isSelected();
		
		memoData.setMemo(memo);
		memoData.setImportant(setImportant);
		
		try {
			OneLineMemoDaoImple memoDao = OneLineMemoDaoImple.getInstance();
			memoDao.update(index, memoData);
			handler.loadOneLineMemoData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dispose();
	} // end updateMemoData()
	
} // end class MemoFrame
