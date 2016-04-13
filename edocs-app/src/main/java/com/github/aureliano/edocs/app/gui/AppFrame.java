package com.github.aureliano.edocs.app.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.github.aureliano.edocs.app.gui.menu.MenuBar;
import com.github.aureliano.edocs.app.model.IDatabaseConnectionDependent;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class AppFrame extends JFrame implements IDatabaseConnectionDependent {

	private static final long serialVersionUID = 7618501026967569839L;

	private EdocsLocale locale;
	private MenuBar menuBar;
	private VerticalToolBar toolBar;
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
		this.toolBar = new VerticalToolBar();
		panel.add(this.toolBar, BorderLayout.WEST);
		
		this.tabbedPane = new TabbedPane();
		panel.add(this.tabbedPane, BorderLayout.CENTER);

		super.setContentPane(panel);
		this.menuBar = new MenuBar();
		super.setJMenuBar(this.menuBar);
		super.addWindowListener(new AppFrameWindowListener());
		
		this.setDatabaseGuiEnabled(false);
	}
	
	@Override
	public void setDatabaseGuiEnabled(boolean enabled) {
		this.menuBar.setDatabaseGuiEnabled(enabled);
		this.toolBar.setDatabaseGuiEnabled(enabled);
	}
	
	public void addTabPanel(String title, JPanel panel) {
		this.tabbedPane.addTab(title, panel);
		
		int index = this.tabbedPane.getTabCount() - 1;
		this.tabbedPane.setSelectedIndex(index);
	}
	
	public void removeTab(int index) {
		this.tabbedPane.removeTabAt(index);
	}
	
	public void removeAllTabs() {
		this.tabbedPane.removeAll();
	}
	
	public int getActiveTab() {
		return this.tabbedPane.getSelectedIndex();
	}
	
	public VerticalToolBar getToolBar() {
		return this.toolBar;
	}
	
	public JTabbedPane getTabbedPane() {
		return this.tabbedPane;
	}
	
	public void showFrame() {
		super.pack();
		super.setSize(new Dimension(800, 600));
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}
}