package com.github.aureliano.edocs.app.gui.menu;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 5179328504511930019L;

	public MenuBar() {
		this.addMenus();
	}

	private void addMenus() {
		super.add(new FileMenu());
	}
}