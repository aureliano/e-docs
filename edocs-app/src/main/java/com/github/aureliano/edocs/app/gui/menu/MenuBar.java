package com.github.aureliano.edocs.app.gui.menu;

import javax.swing.JMenuBar;

import com.github.aureliano.edocs.app.gui.menu.doc.DocumentMenu;
import com.github.aureliano.edocs.app.gui.menu.file.FileMenu;
import com.github.aureliano.edocs.app.gui.menu.help.HelpMenu;
import com.github.aureliano.edocs.app.model.IDatabaseConnectionDependent;

public class MenuBar extends JMenuBar implements IDatabaseConnectionDependent {

	private static final long serialVersionUID = 5179328504511930019L;
	
	private FileMenu fileMenu;
	private DocumentMenu documentMenu;
	private HelpMenu helpMenu;

	public MenuBar() {
		this.addMenus();
	}

	private void addMenus() {
		this.fileMenu = new FileMenu();
		this.documentMenu = new DocumentMenu();
		this.helpMenu = new HelpMenu();
		
		super.add(this.fileMenu);
		super.add(this.documentMenu);
		super.add(this.helpMenu);
	}

	@Override
	public void setDatabaseGuiEnabled(boolean enabled) {
		this.fileMenu.setDatabaseGuiEnabled(enabled);
		this.documentMenu.setDatabaseGuiEnabled(enabled);
		this.helpMenu.setDatabaseGuiEnabled(enabled);
	}
}