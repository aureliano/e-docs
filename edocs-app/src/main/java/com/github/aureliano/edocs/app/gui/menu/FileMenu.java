package com.github.aureliano.edocs.app.gui.menu;

import javax.swing.JMenu;
import javax.swing.JSeparator;

public class FileMenu extends JMenu {

	private static final long serialVersionUID = -662548298147505185L;

	private CloseTabMenuItem closeTabMenuItem;
	private CloseAllTabsMenuItem closeAllTabsMenuItem;
	private ExitMenuItem exitMenuItem;
	
	public FileMenu() {
		super.setText("File");
		this.addMenuItems();
	}

	private void addMenuItems() {
		this.closeTabMenuItem = new CloseTabMenuItem();
		this.closeAllTabsMenuItem = new CloseAllTabsMenuItem();
		this.exitMenuItem = new ExitMenuItem();
		
		super.add(this.closeTabMenuItem);
		super.add(this.closeAllTabsMenuItem);
		
		super.add(new JSeparator());
		
		super.add(this.exitMenuItem);
	}
}