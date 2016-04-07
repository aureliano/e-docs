package com.github.aureliano.edocs.app;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class EdocsApp {

	private static void createAndShowGui() {
		JFrame frame = new JFrame("e-Docs");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(true);
		frame.setContentPane(panel);
		
		frame.pack();
		frame.setVisible(true);
		
		frame.setSize(new Dimension(500, 500));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				createAndShowGui();
			}
		});
	}
}