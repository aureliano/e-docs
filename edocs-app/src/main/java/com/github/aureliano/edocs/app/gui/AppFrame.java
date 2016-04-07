package com.github.aureliano.edocs.app.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.aureliano.edocs.app.gui.menu.MenuBar;

public class AppFrame extends JFrame {

	private static final long serialVersionUID = 7618501026967569839L;

	public AppFrame() {
		this.buildGui();
	}
	
	private void buildGui() {
		super.setTitle("e-Docs");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(true);
		super.setContentPane(panel);
		
		super.setJMenuBar(new MenuBar());
	}
	
	public void showFrame() {
		super.pack();
		super.setVisible(true);
		super.setSize(new Dimension(500, 500));
	}
}