package com.github.aureliano.edocs.app.gui.menu;

import javax.swing.JMenu;

public class FileMenu extends JMenu {

	private static final long serialVersionUID = -662548298147505185L;

	public FileMenu() {
		super.setText("File");
		this.addMenuItems();
	}

	private void addMenuItems() {
		super.add(new ExitMenuItem());
	}
}