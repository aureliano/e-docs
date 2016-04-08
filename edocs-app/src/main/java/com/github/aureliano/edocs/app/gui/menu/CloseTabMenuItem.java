package com.github.aureliano.edocs.app.gui.menu;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class CloseTabMenuItem extends JMenuItem {

	private static final long serialVersionUID = -3834903211976535165L;

	public CloseTabMenuItem() {
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.file.close"));
	}
}