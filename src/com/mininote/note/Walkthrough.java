package com.mininote.note;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class Walkthrough extends JFrame {
	public Walkthrough() {
		super("Sample app");
		this.setLayout(new FlowLayout());
		this.add(new JButton("button"));
		this.add(new JCheckBox("check"));
		this.add(new JLabel("label"));

		this.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));
		this.setSize(new Dimension(250, 80));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
//					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");
				} catch (Exception e) {
					System.out.println("Substance Graphite failed to initialize");
				}
				Walkthrough w = new Walkthrough();
				w.setVisible(true);
			}
		});
	}
}