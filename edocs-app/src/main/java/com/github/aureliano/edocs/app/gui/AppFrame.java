package com.github.aureliano.edocs.app.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.aureliano.edocs.app.gui.menu.MenuBar;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class AppFrame extends JFrame {

	private static final long serialVersionUID = 7618501026967569839L;

	private EdocsLocale locale;
	private TabbedPane tabbedPane;
	
	public AppFrame() {
		this.locale = EdocsLocale.instance();
		this.buildGui();
	}
	
	private void buildGui() {
		super.setTitle(this.locale.getMessage("gui.frame.main.title"));
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(true);
		panel.add(new VerticalToolBar(), BorderLayout.WEST);
		
		this.tabbedPane = new TabbedPane();
		panel.add(this.tabbedPane, BorderLayout.CENTER);
		
		super.setContentPane(panel);
		super.setJMenuBar(new MenuBar());
	}
	
	public void showFrame() {
		super.pack();
		super.setSize(new Dimension(500, 500));
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}
}