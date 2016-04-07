package com.github.aureliano.edocs.app.gui.menu;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 5179328504511930019L;
	
	private FileMenu fileMenu;
	private DocumentMenu documentMenu;

	public MenuBar() {
		this.addMenus();
	}

	private void addMenus() {
		this.fileMenu = new FileMenu();
		this.documentMenu = new DocumentMenu();
		
		super.add(this.fileMenu);
		super.add(this.documentMenu);
	}
}